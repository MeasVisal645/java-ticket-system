package com.ticketsystem.Controller;

import com.ticketsystem.Entities.User;
import com.ticketsystem.Service.UserService;
import com.ticketsystem.Utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public Mono<ApiResponse<User>> update(@RequestBody User user) {
        return userService.update(user)
                .map(updatedUser -> new ApiResponse<>(HttpStatus.OK, "Success", updatedUser));
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ApiResponse<Void>> delete(@PathVariable Long id) {
        return userService.delete(id)
                .map(ticket -> new ApiResponse<>(HttpStatus.OK, "Success", ticket));
    }
}
