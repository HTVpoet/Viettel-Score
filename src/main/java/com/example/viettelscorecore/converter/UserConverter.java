package com.example.viettelscorecore.converter;

import com.example.viettelscorecore.model.dto.request.UserCreateRequest;
import com.example.viettelscorecore.model.dto.response.UserResponse;
import com.example.viettelscorecore.model.entity.Users;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public UserResponse toResponse(Users user){
        return UserResponse.builder()
                .phone(user.getPhone())
                .email(user.getEmail())
                .address(user.getAddress())
                .name(user.getName())
                .password(user.getPassword())
                .build();
    }

    public Users toEntity(UserCreateRequest request){
        return Users.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .address(request.getAddress())
                .password(request.getPassword())
                .build();
    }
}
