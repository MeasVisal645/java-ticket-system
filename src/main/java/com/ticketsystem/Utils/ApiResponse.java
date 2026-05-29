package com.ticketsystem.Utils;

import org.springframework.http.HttpStatus;

public record ApiResponse<T>(
        HttpStatus status,
        String message,
        T data
) {

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(
                HttpStatus.OK,
                "Success",
                data
        );
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(
                HttpStatus.OK,
                message,
                data
        );
    }

    public static ApiResponse<Void> deleted(String message) {
        return new ApiResponse<>(
                HttpStatus.OK,
                message,
                null
        );
    }

    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(
                HttpStatus.CREATED,
                "Created Success",
                data
        );
    }

    public static <T> ApiResponse<T> updated(T data) {
        return new ApiResponse<>(
                HttpStatus.OK,
                "Updated Success",
                data
        );
    }
}