package com.ticketsystem.Service;

import com.ticketsystem.Dto.AuthRequest;
import com.ticketsystem.Dto.AuthResponse;
import com.ticketsystem.Dto.UserDto;
import com.ticketsystem.Dto.UsernameResponse;
import com.ticketsystem.Entities.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface UserService {
    Flux<UsernameResponse> FindAllUsers();
    Mono<User> create(User user);
    Mono<User> update(User user);
    Mono<Void> delete(Long id);
    Mono<AuthResponse> signIn(AuthRequest request);
    Mono<AuthResponse> refreshToken(String token);
    Mono<UserDto> currentUser();
}
