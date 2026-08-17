package com.example.viettelscorecore.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TokenResponse {
    private String tokenType;
    private String accessToken;
    private long expiresIn;
    private String username;
}
