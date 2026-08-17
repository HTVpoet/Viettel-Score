package com.example.viettelscorecore.service.impl;

import com.example.viettelscorecore.converter.UserConverter;
import com.example.viettelscorecore.model.dto.request.UserCreateRequest;
import com.example.viettelscorecore.model.dto.response.AvatarUploadResponse;
import com.example.viettelscorecore.model.dto.response.BatchFileUploadResponse;
import com.example.viettelscorecore.model.dto.response.FileUploadDetail;
import com.example.viettelscorecore.model.dto.response.UserResponse;
import com.example.viettelscorecore.model.entity.Users;
import com.example.viettelscorecore.repository.UsersRepository;
import com.example.viettelscorecore.service.FileStorageService;
import com.example.viettelscorecore.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final UserConverter userConverter;
    private final FileStorageService fileStorageService;
    private final PasswordEncoder passwordEncoder;
    private static final int MAX_FILES = 5;


    /**
     * Create a new user. This method takes a UserCreateRequest DTO, encodes the password,
     * converts it to a Users entity, saves it to the database, and returns a UserResponse DTO.
     * @param request
     * @return
     */
    @Override
    @Transactional
    public UserResponse createUser(UserCreateRequest request) {
        log.info("Create user with request, {}", request);
        Users user = userConverter.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userConverter.toResponse(usersRepository.save(user));
    }

    /**
     * Get user by ID. This method retrieves a user from the database based on the provided userId and converts it to a UserResponse DTO.
     * @param userId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long userId) {
        log.info("Get user by id: {}", userId);
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return userConverter.toResponse(user);
    }

    /**
     * Get all users. This method retrieves all users from the database and converts them to UserResponse DTOs.
     * @return List of UserResponse
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> search(String keyword) {
        log.info("Search users by keyword: {}", keyword);
        return usersRepository.search(keyword)
                .stream()
                .map(userConverter::toResponse)
                .toList();
    }

    /**
     * Update the avatar path for a user. This method updates the user's avatar field in the database with the provided avatar path.
     * @param userId
     * @param avatarPath
     */
    @Override
    @Transactional
    public void updateUserAvatar(Long userId, String avatarPath) {
        log.info("Update user avatar for userId: {} with path: {}", userId, avatarPath);
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        user.setAvatar(avatarPath);
        usersRepository.save(user);
    }

    /**
     * Upload the avatar for a user. The avatar file is uploaded to the storage and the user's avatar field is updated with the path.
     * @param userId
     * @param avatarFile
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity<AvatarUploadResponse> uploadAvatar(Long userId, MultipartFile avatarFile) {
        if (avatarFile.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    AvatarUploadResponse.builder()
                            .message("File is empty")
                            .build()
            );
        }

        try {
            String objectName = "avatars/" + userId + "/" + UUID.randomUUID() + "-" + avatarFile.getOriginalFilename();
            fileStorageService.uploadFile(avatarFile, objectName);
            return ResponseEntity.ok(
                    AvatarUploadResponse.builder()
                            .userId(userId)
                            .avatarUrl(objectName)
                            .message("Avatar uploaded successfully")
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    AvatarUploadResponse.builder()
                            .message("Failed to upload avatar: " + e.getMessage())
                            .build()
            );
        }
    }

    /**
     * Download the avatar of a user. If the user has an avatar, it will be downloaded from the storage and returned as a response.
     * @param userId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<InputStreamResource> downloadAvatar(Long userId) {
        try {
            Users user = usersRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

            if (user.getAvatar() == null || user.getAvatar().isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            InputStream inputStream = fileStorageService.downloadFile(user.getAvatar());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + user.getAvatar() + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new InputStreamResource(inputStream));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Delete the avatar of a user. If the user has an avatar, it will be deleted from the storage and the user's avatar field will be set to null.
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity<Void> deleteAvatar(Long userId) {
        try {
            Users user = usersRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

            if (user.getAvatar() != null && !user.getAvatar().isEmpty()) {
                fileStorageService.deleteFile(user.getAvatar());
                updateUserAvatar(userId, null);
            }

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     *  Batch upload files for a user. Validates the number of files and handles each file upload individually.
     * @param userId
     * @param files
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity<BatchFileUploadResponse> batchUploadFiles(Long userId, MultipartFile[] files) {
        if (files == null || files.length == 0) {
            return ResponseEntity.badRequest().body(
                    BatchFileUploadResponse.builder()
                            .userId(userId)
                            .totalFiles(0)
                            .successCount(0)
                            .failureCount(0)
                            .files(new ArrayList<>())
                            .message("No files provided")
                            .build()
            );
        }

        if (files.length > MAX_FILES) {
            return ResponseEntity.badRequest().body(
                    BatchFileUploadResponse.builder()
                            .userId(userId)
                            .totalFiles(files.length)
                            .successCount(0)
                            .failureCount(files.length)
                            .files(new ArrayList<>())
                            .message("Maximum " + MAX_FILES + " files allowed per upload. Provided: " + files.length)
                            .build()
            );
        }

        List<FileUploadDetail> uploadedFiles = new ArrayList<>();
        int successCount = 0;
        int failureCount = 0;

        for (MultipartFile file : files) {
            try {
                if (file.isEmpty()) {
                    uploadedFiles.add(
                            FileUploadDetail.builder()
                                    .fileName(file.getOriginalFilename())
                                    .status("FAILED")
                                    .message("File is empty")
                                    .build()
                    );
                    failureCount++;
                    continue;
                }

                String objectName = "files/" + userId + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
                uploadFile(file, objectName);

                uploadedFiles.add(
                        FileUploadDetail.builder()
                                .fileName(file.getOriginalFilename())
                                .objectName(objectName)
                                .fileSize(file.getSize())
                                .contentType(file.getContentType())
                                .status("SUCCESS")
                                .message("File uploaded successfully")
                                .build()
                );
                successCount++;
            } catch (Exception e) {
                uploadedFiles.add(
                        FileUploadDetail.builder()
                                .fileName(file.getOriginalFilename())
                                .status("FAILED")
                                .message("Error uploading file: " + e.getMessage())
                                .build()
                );
                failureCount++;
            }
        }

        return ResponseEntity.ok(
                BatchFileUploadResponse.builder()
                        .userId(userId)
                        .totalFiles(files.length)
                        .successCount(successCount)
                        .failureCount(failureCount)
                        .files(uploadedFiles)
                        .message(successCount + " file(s) uploaded successfully, " + failureCount + " failed")
                        .build()
        );
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {

            Users user = usersRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

            // Delete avatar if exists
            if (user.getAvatar() != null && !user.getAvatar().isEmpty()) {
                fileStorageService.deleteFile(user.getAvatar());
            }
            // Delete user
            usersRepository.delete(user);
    }

    @Override
    public UserResponse updateUser(Long userId, UserCreateRequest request) {
            Users existingUser = usersRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

            // Update fields
            existingUser.setName(request.getName());
            existingUser.setEmail(request.getEmail());
            existingUser.setAddress(request.getAddress());
            existingUser.setPhone(request.getPhone());
            if (request.getPassword() != null && !request.getPassword().isBlank()) {
                existingUser.setPassword(passwordEncoder.encode(request.getPassword()));
            }

            Users updatedUser = usersRepository.save(existingUser);
            return userConverter.toResponse(updatedUser);
    }

    public void uploadFile(MultipartFile file, String objectName) {
        log.info("Uploading file with objectName: {}", objectName);
        fileStorageService.uploadFile(file, objectName);
    }
}
