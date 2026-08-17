package com.example.viettelscorecore.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequest {
    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;
    @NotNull(message = "Email cannot be null")
    @Size(min = 5, max = 100, message = "Email must be between 5 and 100 characters")
    private String email;
    @NotNull(message = "Address cannot be null")
    @Size(min = 5, max = 200, message = "Address must be between 5 and 200 characters")
    private String address;
    @NotNull(message = "Phone cannot be null")
    @Size(min = 10, max = 15, message = "Phone must be between 10 and 15 characters")
    private String phone;
    @NotNull(message = "Password cannot be null")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    private String password;
}
