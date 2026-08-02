package com.example.viettelscorecore.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequest {
    private String name;
    private String email;
    private String address;
    private String phone;
    private String password;
}
