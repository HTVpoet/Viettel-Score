package com.example.viettelscorecore.controller;

import com.example.viettelscorecore.model.dto.request.UserCreateRequest;
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
    public UserResponse create(@RequestBody UserCreateRequest request) {
        return usersService.createUser(request);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = usersService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        try {
            UserResponse user = usersService.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
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
}