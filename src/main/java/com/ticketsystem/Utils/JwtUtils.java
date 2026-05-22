package com.ticketsystem.Utils;


import com.ticketsystem.Entities.User;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@Data
public class JwtUtils {

    private final Key secretKey;
    private final Long accessExpiration;
    private final Long refreshExpiration;

    public JwtUtils(Dotenv dotenv) {
        this.secretKey = Keys.hmacShaKeyFor(dotenv.get("JWT_SECRET").getBytes());
        this.accessExpiration = Long.parseLong(dotenv.get("JWT_ACCESS_EXPIRATION", "600000"));
        this.refreshExpiration = Long.parseLong(dotenv.get("JWT_REFRESH);_EXPIRATION", "1209600000"));
    }

    private Claims claims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            claims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isRefreshToken(String token) {
        try {
            return "refresh".equals(claims(token).get("type", String.class));
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUsername(String token) { return claims(token).getSubject(); }
    public String extractRole(String token) { return claims(token).get("role", String.class); }
    public Long extractUserId(String token) { return claims(token).get("id", Long.class); }

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("id", user.getId())
                .claim("role", "ROLE_" + user.getRole().name())
                .claim("type", "access")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(secretKey)
                .compact();
    }

    public String generateRefreshToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("id", user.getId())
                .claim("role", "ROLE_" + user.getRole().name())
                .claim("type", "refresh")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(secretKey)
                .compact();
    }

    public String refreshAccessToken(String refreshToken) {
        if (!validateToken(refreshToken) || !isRefreshToken(refreshToken)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        String username = extractUsername(refreshToken);
        String role = extractRole(refreshToken);
        Long userId = extractUserId(refreshToken);

        return Jwts.builder()
                .setSubject(username)
                .claim("id", userId)
                .claim("role", role)
                .claim("type", "access")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(secretKey)
                .compact();
    }
}
