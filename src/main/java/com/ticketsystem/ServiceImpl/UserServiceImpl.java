package com.ticketsystem.ServiceImpl;

import com.ticketsystem.Dto.AuthRequest;
import com.ticketsystem.Dto.AuthResponse;
import com.ticketsystem.Dto.UserDto;
import com.ticketsystem.Dto.UsernameResponse;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public Mono<AuthResponse> signIn(AuthRequest request) {
        return userRepository
                .findByUsername(request.username())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password")))
                .flatMap(user -> {
                    boolean matches = passwordEncoder.matches(
                                    request.password(),
                                    user.getPassword());
                    if (!matches) {
                        return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password"));
                    }

                    if (!user.getIsActive()) {
                        return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "User account is inactive"));
                    }

                    String accessToken = jwtUtils.generateAccessToken(user);
                    String refreshToken = jwtUtils.generateRefreshToken(user);

                    return Mono.just(new AuthResponse(accessToken, refreshToken));
                });
    }

    @Override
    public Mono<AuthResponse> refreshToken(String refreshToken) {
        if (refreshToken == null || refreshToken.isBlank()) {
            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token required"));
        }

        if (!jwtUtils.validateToken(refreshToken)) {
            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token"));
        }

        if (!jwtUtils.isRefreshToken(refreshToken)) {
            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token"));
        }

        String username = jwtUtils.extractUsername(refreshToken);

        return userRepository
                .findByUsername(username)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found")))
                .map(user -> {
                    String accessToken = jwtUtils.generateAccessToken(user);
                    return new AuthResponse(accessToken, null);
                });
    }

    @Override
    public Mono<UserDto> currentUser() {
        return ReactiveSecurityContextHolder.getContext()
                .mapNotNull(SecurityContext::getAuthentication)
                .map(Authentication::getName)
                .flatMap(userRepository::findByUsername)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Credentials")))
                .map(UserMapper.INSTANCE::toDto);
    }

    @Override
    public Flux<UsernameResponse> FindAllUsers() {
        return userRepository.findAll()
                .map(user -> new UsernameResponse(user.getId(), user.getUsername()));
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
