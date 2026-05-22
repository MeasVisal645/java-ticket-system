package com.ticketsystem.Controller;

import com.ticketsystem.Dto.AuthRequest;
import com.ticketsystem.Dto.AuthResponse;
import com.ticketsystem.Entities.User;
import com.ticketsystem.Service.UserService;
import com.ticketsystem.Utils.CookieUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public Mono<User> signUp(@RequestBody User user) {
        return userService.create(user);
    }

    @PostMapping("/signin")
    public Mono<ResponseEntity<AuthResponse>> signIn(@RequestBody AuthRequest req) {
        return userService.signIn(req)
                .map(tokens -> ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, CookieUtil.responseCookie(tokens.refreshToken()).toString())
                        .body(new AuthResponse(tokens.accessToken(), tokens.refreshToken()))
                );
    }

    @PostMapping("/refresh")
    public Mono<AuthResponse> refreshToken(@CookieValue(name = "refreshToken", required = false) String refreshToken) {
        return userService.refreshToken(refreshToken);
    }
}
