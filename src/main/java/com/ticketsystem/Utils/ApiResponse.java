package com.ticketsystem.Utils;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

public record ApiResponse<T>(

        @Schema(allowableValues = {"200 OK", "201 CREATED", "400 BAD_REQUEST", "401 UNAUTHORIZED", "403 FORBIDDEN", "404 NOT_FOUND", "500 INTERNAL_SERVER_ERROR"})
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