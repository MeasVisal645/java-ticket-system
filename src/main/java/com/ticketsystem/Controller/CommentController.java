package com.ticketsystem.Controller;

import com.ticketsystem.Dto.CommentDto;
import com.ticketsystem.Service.CommentService;
import com.ticketsystem.Utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/ticket-comment")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{id}")
    public Mono<ApiResponse<?>> findById(@PathVariable Long id) {
        return commentService.findById(id)
                .map(ApiResponse::success);
    }

    @GetMapping("/ticketId/{ticketId}")
    public Mono<ApiResponse<?>> findByTicketId(@PathVariable Long ticketId) {
        return commentService.findByTicketId(ticketId)
                .collectList()
                .map(ApiResponse::success);
    }

    @PostMapping("/create")
    public Mono<ApiResponse<?>> create(@Valid @RequestBody CommentDto commentDto) {
        return commentService.create(commentDto)
                .map(ApiResponse::created);
    }

    @PutMapping("/update/{id}")
    public Mono<ApiResponse<?>> update(@Valid @RequestBody CommentDto commentDto) {
        return commentService.update(commentDto)
                .map(ApiResponse::success);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ApiResponse<?>> delete(@PathVariable Long id) {
        return commentService.delete(id)
                .thenReturn(ApiResponse.deleted("Delete Success"));
    }
}
