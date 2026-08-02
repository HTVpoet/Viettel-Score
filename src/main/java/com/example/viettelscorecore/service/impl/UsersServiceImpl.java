package com.example.viettelscorecore.service.impl;

import com.example.viettelscorecore.converter.UserConverter;
import com.example.viettelscorecore.model.dto.request.UserCreateRequest;
import com.example.viettelscorecore.model.dto.response.UserResponse;
import com.example.viettelscorecore.model.entity.Users;
import com.example.viettelscorecore.repository.UsersRepository;
import com.example.viettelscorecore.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final UserConverter userConverter;

    @Override
    public UserResponse createUser(UserCreateRequest request) {
        log.info("Create user with request, {}", request);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        request.setPassword(encoder.encode(request.getPassword()));
        return userConverter.toResponse(usersRepository.save(userConverter.toEntity(request)));
    }

    @Override
    public UserResponse getUserById(Long userId) {
        log.info("Get user by id: {}", userId);
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return userConverter.toResponse(user);
    }

    @Override
    public void updateUserAvatar(Long userId, String avatarPath) {
        log.info("Update user avatar for userId: {} with path: {}", userId, avatarPath);
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        user.setAvatar(avatarPath);
        usersRepository.save(user);
    }
}
