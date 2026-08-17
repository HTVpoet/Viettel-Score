package com.example.viettelscorecore.service.impl;

import com.example.viettelscorecore.converter.UserConverter;
import com.example.viettelscorecore.model.dto.request.LoginRequest;
import com.example.viettelscorecore.model.dto.request.RegisterRequest;
import com.example.viettelscorecore.model.dto.response.TokenResponse;
import com.example.viettelscorecore.model.dto.response.UserResponse;
import com.example.viettelscorecore.model.entity.Users;
import com.example.viettelscorecore.repository.UsersRepository;
import com.example.viettelscorecore.security.JwtProperties;
import com.example.viettelscorecore.security.JwtService;
import com.example.viettelscorecore.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsersRepository usersRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;

    @Override
    @Transactional
    public UserResponse register(RegisterRequest request) {
        if (usersRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already registered: " + request.getEmail());
        }

        Users user = new Users();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Users saved = usersRepository.save(user);
        return userConverter.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public TokenResponse login(LoginRequest request) {
        Users user = usersRepository.findByEmail(request.getUsername())
                .or(() -> usersRepository.findByPhone(request.getUsername()))
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getEmail());
        return TokenResponse.builder()
                .tokenType("Bearer")
                .accessToken(token)
                .expiresIn(jwtProperties.getExpirationMs())
                .username(user.getEmail())
                .build();
    }
}
