package com.example.viettelscorecore.service;

import com.example.viettelscorecore.model.dto.request.LoginRequest;
import com.example.viettelscorecore.model.dto.request.RegisterRequest;
import com.example.viettelscorecore.model.dto.response.TokenResponse;
import com.example.viettelscorecore.model.dto.response.UserResponse;

public interface AuthService {
    UserResponse register(RegisterRequest request);

    TokenResponse login(LoginRequest request);
}
