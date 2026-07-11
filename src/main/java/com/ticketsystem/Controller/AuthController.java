package com.ticketsystem.Controller;

import com.ticketsystem.Dto.AuthRequest;
import com.ticketsystem.Dto.AuthResponse;
import com.ticketsystem.Dto.UserDto;
import com.ticketsystem.Entities.User;
import com.ticketsystem.Service.UserService;
import com.ticketsystem.Utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public Mono<ApiResponse<?>> signUp(@Valid @RequestBody User user) {
        return userService.create(user)
                .map(ApiResponse::success);
    }

    @PostMapping("/signin")
    public Mono<ApiResponse<AuthResponse>> signIn(@RequestBody AuthRequest request, ServerHttpResponse response) {
        return userService.signIn(request)
                .map(tokens -> {
                    ResponseCookie refreshCookie = ResponseCookie.from(
                                            "refreshToken",
                                            tokens.refreshToken())
                                    .httpOnly(true)
                                    .secure(true)
                                    .path("/")
                                    .maxAge(Duration.ofDays(7))
                                    .sameSite("Lax")
                                    .build();

                    response.addCookie(refreshCookie);

                    return ApiResponse.success(
                            new AuthResponse(
                                    tokens.accessToken(),
                                    null
                            )
                    );
                });
    }

    @PostMapping("/refresh")
    public Mono<ApiResponse<AuthResponse>> refresh(@CookieValue(name = "refreshToken", required = false) String refreshToken) {
        return userService.refreshToken(refreshToken)
                .map(ApiResponse::success);
    }

    @GetMapping("/me")
    public Mono<ApiResponse<UserDto>> me() {
        return userService.currentUser()
                .map(ApiResponse::success);
    }
}
