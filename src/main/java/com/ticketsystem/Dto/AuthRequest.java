package com.ticketsystem.Dto;

public record AuthRequest(
        String username,
        String password
) {
}
