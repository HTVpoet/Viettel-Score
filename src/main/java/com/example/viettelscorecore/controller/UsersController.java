package com.example.viettelscorecore.controller;

import com.example.viettelscorecore.model.dto.request.UserCreateRequest;
import com.example.viettelscorecore.model.dto.request.UserSearchRequest;
import com.example.viettelscorecore.model.dto.response.ApiResponse;
import com.example.viettelscorecore.model.dto.response.AvatarUploadResponse;
import com.example.viettelscorecore.model.dto.response.BatchFileUploadResponse;
import com.example.viettelscorecore.model.dto.response.UserResponse;
import com.example.viettelscorecore.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/public/api/v1/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @PostMapping
    public ApiResponse<UserResponse> create(@RequestBody UserCreateRequest request) {
        return ApiResponse.created(usersService.createUser(request));
    }

    @PostMapping("/search")
    public ApiResponse<List<UserResponse>> search(@RequestBody UserSearchRequest request) {
        return ApiResponse.success(usersService.search(request.getKeywords()));
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserResponse> getUserById(@PathVariable Long userId) {
        return ApiResponse.success(usersService.getUserById(userId));
    }

    @PostMapping("/{userId}/avatar")
    public ResponseEntity<AvatarUploadResponse> uploadAvatar(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file) throws IOException {
        return usersService.uploadAvatar(userId, file);
    }

    @GetMapping("/{userId}/avatar")
    public ResponseEntity<InputStreamResource> downloadAvatar(@PathVariable Long userId) {
        return usersService.downloadAvatar(userId);
    }

    @DeleteMapping("/{userId}/avatar")
    public ResponseEntity<Void> deleteAvatar(@PathVariable Long userId) {
        return usersService.deleteAvatar(userId);
    }

    @PostMapping("/{userId}/batch-upload")
    public ResponseEntity<BatchFileUploadResponse> batchUploadFiles(
            @PathVariable Long userId,
            @RequestParam("files") MultipartFile[] files) {
        return usersService.batchUploadFiles(userId, files);
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<Void> deleteUser(@PathVariable Long userId) {
        usersService.deleteUser(userId);
        return ApiResponse.success("User deleted successfully", null);
    }

    @PutMapping("/{userId}")
    public ApiResponse<UserResponse> updateUser(@PathVariable Long userId, @RequestBody UserCreateRequest request) {
        return ApiResponse.success(usersService.updateUser(userId, request));
    }
}