package com.ticketsystem.Exception;

import com.ticketsystem.Utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<Object>> handleResponseStatusException(
            ResponseStatusException ex
    ) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(new ApiResponse<>(
                        HttpStatus.valueOf(ex.getStatusCode().value()),
                        ex.getReason(),
                        null
                ));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<Object>> handleAuthenticationException(
            AuthenticationException ex
    ) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse<>(
                        HttpStatus.UNAUTHORIZED,
                        "Unauthorized",
                        null
                ));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Object>> handleAccessDeniedException(
            AccessDeniedException ex
    ) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ApiResponse<>(
                        HttpStatus.FORBIDDEN,
                        "Access Denied",
                        null
                ));
    }
}