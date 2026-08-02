package com.example.viettelscorecore.service;

import com.example.viettelscorecore.model.dto.request.UserCreateRequest;
import com.example.viettelscorecore.model.dto.response.UserResponse;


public interface UsersService {
    UserResponse createUser(UserCreateRequest request);
    UserResponse getUserById(Long userId);
    void updateUserAvatar(Long userId, String avatarPath);
}