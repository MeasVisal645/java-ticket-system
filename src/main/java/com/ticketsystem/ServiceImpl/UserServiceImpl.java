package com.ticketsystem.ServiceImpl;

import com.ticketsystem.Dto.AuthRequest;
import com.ticketsystem.Dto.AuthResponse;
import com.ticketsystem.Dto.UserDto;
import com.ticketsystem.Entities.Role;
import com.ticketsystem.Entities.User;
import com.ticketsystem.Mapper.UserMapper;
import com.ticketsystem.Repository.UserRepository;
import com.ticketsystem.Service.UserService;
import com.ticketsystem.Utils.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public Mono<AuthResponse> signIn(AuthRequest request) {
        if (request.username() == null || request.username().isBlank()
                || request.password() == null || request.password().isBlank()) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username or password is required"));
        }

        return userRepository.findByUsername(request.username())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")))
                .flatMap(user -> {

                    if (!user.getIsActive()) {
                        return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "User account is inactive"));
                    }

                    if (!passwordEncoder.matches(request.password(), user.getPassword())) {
                        return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));
                    }

                    String access = jwtUtils.generateAccessToken(user);
                    String refresh = jwtUtils.generateRefreshToken(user);

                    return Mono.just(new AuthResponse(access, refresh));
                });
    }

    @Override
    public Mono<AuthResponse> refreshToken(String token) {
        if (token == null || token.isBlank()) {
            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token is required"));
        }
        if (!jwtUtils.validateToken(token) || !jwtUtils.isRefreshToken(token)) {
            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token"));
        }

        String newAccessToken = jwtUtils.refreshAccessToken(token);

        return Mono.just(new AuthResponse(newAccessToken, null));
    }

    @Override
    public Mono<UserDto> currentUser() {
        return ReactiveSecurityContextHolder.getContext()
                .mapNotNull(SecurityContext::getAuthentication)
                .map(Authentication::getName)
                .flatMap(userRepository::findByUsername)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
                ))
                .map(UserMapper::toDto);
    }

    @Override
    public Mono<User> create(User user) {
        var encodedPassword = passwordEncoder.encode(user.getPassword());

        return userRepository.save(
                User.from(user)
                        .role(Role.USER)
                        .password(encodedPassword)
                        .isActive(true)
                        .build()
        );
    }

    @Override
    public Mono<User> update(User user) {
        var encodedPassword = passwordEncoder.encode(user.getPassword());
        return userRepository.findById(user.getId())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")))
                .flatMap(existingUser -> {
                    User.update(existingUser, user);
                    existingUser.setPassword(encodedPassword);
                    return userRepository.save(existingUser);
                });
    }

    @Override
    public Mono<Void> delete(Long id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")))
                .flatMap(userRepository::delete);
    }
}
