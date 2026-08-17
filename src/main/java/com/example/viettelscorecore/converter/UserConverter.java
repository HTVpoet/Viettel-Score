package com.example.viettelscorecore.converter;

import com.example.viettelscorecore.model.dto.request.UserCreateRequest;
import com.example.viettelscorecore.model.dto.response.UserResponse;
import com.example.viettelscorecore.model.entity.Users;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public UserResponse toResponse(Users user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .avatar(user.getAvatar())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .deletedAt(user.getDeletedAt())
                .deleted(user.getDeleted())
                .createdBy(user.getCreatedBy())
                .updatedBy(user.getUpdatedBy())
                .deletedBy(user.getDeletedBy())
                .build();
    }

    public Users toEntity(UserCreateRequest request) {
        Users user = new Users();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setAddress(request.getAddress());
        user.setPhone(request.getPhone());
        user.setPassword(request.getPassword());
        return user;
    }
}
