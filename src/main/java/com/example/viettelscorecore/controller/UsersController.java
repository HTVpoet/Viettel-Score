package com.example.viettelscorecore.controller;

import com.example.viettelscorecore.model.dto.request.UserCreateRequest;
import com.example.viettelscorecore.model.dto.response.AvatarUploadResponse;
import com.example.viettelscorecore.model.dto.response.UserResponse;
import com.example.viettelscorecore.service.UsersService;
import com.example.viettelscorecore.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;


@RestController
@RequestMapping("/public/api/v1/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;
    private final FileStorageService fileStorageService;

    @PostMapping
    public UserResponse create(@RequestBody UserCreateRequest request) {
        return usersService.createUser(request);
    }

    @PostMapping("/{userId}/avatar")
    public ResponseEntity<AvatarUploadResponse> uploadAvatar(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    AvatarUploadResponse.builder()
                            .message("File is empty")
                            .build()
            );
        }

        try {
            String objectName = "avatars/" + userId + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
            fileStorageService.uploadFile(file, objectName);
            usersService.updateUserAvatar(userId, objectName);

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

    @GetMapping("/{userId}/avatar")
    public ResponseEntity<InputStreamResource> downloadAvatar(@PathVariable Long userId) {
        try {
            UserResponse user = usersService.getUserById(userId);

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

    @DeleteMapping("/{userId}/avatar")
    public ResponseEntity<Void> deleteAvatar(@PathVariable Long userId) {
        try {
            UserResponse user = usersService.getUserById(userId);

            if (user.getAvatar() != null && !user.getAvatar().isEmpty()) {
                fileStorageService.deleteFile(user.getAvatar());
                usersService.updateUserAvatar(userId, null);
            }

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}