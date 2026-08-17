package com.example.viettelscorecore.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;
    private ZonedDateTime timestamp;

    private static ZonedDateTime now() {
        return ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
    }

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .code(200)
                .message("Success")
                .data(data)
                .timestamp(now())
                .build();
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .code(200)
                .message(message)
                .data(data)
                .timestamp(now())
                .build();
    }

    public static <T> ApiResponse<T> created(T data) {
        return ApiResponse.<T>builder()
                .code(201)
                .message("Created")
                .data(data)
                .timestamp(now())
                .build();
    }

    public static <T> ApiResponse<T> error(int code, String message) {
        return ApiResponse.<T>builder()
                .code(code)
                .message(message)
                .timestamp(now())
                .build();
    }

    public static <T> ApiResponse<T> error(int code, String message, T data) {
        return ApiResponse.<T>builder()
                .code(code)
                .message(message)
                .data(data)
                .timestamp(now())
                .build();
    }
}
