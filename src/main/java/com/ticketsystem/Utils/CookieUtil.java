package com.ticketsystem.Utils;

import org.springframework.http.ResponseCookie;

public class CookieUtil {

    public static ResponseCookie responseCookie(String token) {
        return ResponseCookie.from("refreshToken", token)
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .path("/")
                .maxAge(30L * 24 * 60 * 60)
                .build();
    }
}
