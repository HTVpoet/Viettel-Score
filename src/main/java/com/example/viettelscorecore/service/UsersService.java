package com.example.viettelscorecore.service;

import com.example.viettelscorecore.model.dto.request.UserCreateRequest;
import com.example.viettelscorecore.model.dto.response.AvatarUploadResponse;
import com.example.viettelscorecore.model.dto.response.BatchFileUploadResponse;
import com.example.viettelscorecore.model.dto.response.UserResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface UsersService {
    UserResponse createUser(UserCreateRequest request);
    UserResponse getUserById(Long userId);
    List<UserResponse> search(String keyword);
    void updateUserAvatar(Long userId, String avatarPath);
    ResponseEntity<AvatarUploadResponse> uploadAvatar(Long userId, MultipartFile avatarFile);
    ResponseEntity<InputStreamResource> downloadAvatar(Long userId);
    ResponseEntity<Void> deleteAvatar(Long userId);
    ResponseEntity<BatchFileUploadResponse> batchUploadFiles(Long userId, MultipartFile[] files);
    void deleteUser(Long userId);
    UserResponse updateUser(Long userId, UserCreateRequest request);

}