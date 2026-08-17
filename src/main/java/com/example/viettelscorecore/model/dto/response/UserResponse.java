package com.example.viettelscorecore.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String address;
    private String phone;
    private String avatar;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;
    private Boolean deleted;
    private String createdBy;
    private String updatedBy;
    private String deletedBy;
}
