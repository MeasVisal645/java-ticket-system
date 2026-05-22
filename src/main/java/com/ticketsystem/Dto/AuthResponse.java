package com.ticketsystem.Dto;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {
}
