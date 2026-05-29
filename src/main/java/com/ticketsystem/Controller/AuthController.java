package com.ticketsystem.Controller;

import com.ticketsystem.Dto.AuthRequest;
import com.ticketsystem.Dto.AuthResponse;
import com.ticketsystem.Entities.User;
import com.ticketsystem.Service.UserService;
import com.ticketsystem.Utils.ApiResponse;
import com.ticketsystem.Utils.CookieUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public Mono<ApiResponse<?>> signUp(@RequestBody User user) {
        return userService.create(user)
                .map(ApiResponse::success);
    }

    @PostMapping("/signin")
    public Mono<ResponseEntity<ApiResponse<?>>> signIn(@RequestBody AuthRequest req) {
        return userService.signIn(req)
                .map(tokens -> {
                    AuthResponse response = new AuthResponse(
                            tokens.accessToken(),
                            null);

                    return ResponseEntity.ok()
                            .header(HttpHeaders.SET_COOKIE, CookieUtil.responseCookie(tokens.refreshToken()).toString())
                            .body(ApiResponse.success(response));
                });
    }

    @PostMapping("/refresh")
    public Mono<ApiResponse<?>> refreshToken(@CookieValue(name = "refreshToken", required = false) String refreshToken) {
        return userService.refreshToken(refreshToken)
                .map(tokens -> new ApiResponse<>(HttpStatus.OK, "Success", new AuthResponse(tokens.accessToken(), tokens.refreshToken())));
    }
}
