package com.example.viettelscorecore.controller;

import com.example.viettelscorecore.model.dto.request.LoginRequest;
import com.example.viettelscorecore.model.dto.request.RegisterRequest;
import com.example.viettelscorecore.model.dto.response.ApiResponse;
import com.example.viettelscorecore.model.dto.response.TokenResponse;
import com.example.viettelscorecore.model.dto.response.UserResponse;
import com.example.viettelscorecore.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ApiResponse.created(authService.register(request));
    }

    @PostMapping("/login")
    public ApiResponse<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.success(authService.login(request));
    }
}
