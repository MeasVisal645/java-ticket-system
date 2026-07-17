package com.ticketsystem.Controller;

import com.ticketsystem.Entities.User;
import com.ticketsystem.Service.UserService;
import com.ticketsystem.Utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/allUsers")
    public Mono<ApiResponse<?>> findAllUsers() {
        return userService.FindAllUsers()
                .collectList()
                .map(ApiResponse::success);
    }

    @PutMapping("/update/{id}")
    public Mono<ApiResponse<?>> update(@RequestBody User user) {
        return userService.update(user)
                .map(ApiResponse::updated);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ApiResponse<?>> delete(@PathVariable Long id) {
        return userService.delete(id)
                .thenReturn(ApiResponse.deleted("Delete Success"));
    }

    @GetMapping("/total")
    public Mono<ApiResponse<?>> count() {
        return userService.count()
                .map(ApiResponse::success);
    }
}
