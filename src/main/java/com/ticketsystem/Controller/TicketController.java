package com.ticketsystem.Controller;

import com.ticketsystem.Dto.TicketDto;
import com.ticketsystem.Entities.Priority;
import com.ticketsystem.Entities.Ticket;
import com.ticketsystem.Service.TicketService;
import com.ticketsystem.Utils.ApiResponse;
import com.ticketsystem.Utils.PageResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/v1/ticket")
@AllArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @GetMapping("/all")
    public Mono<ApiResponse<?>> findALl() {
        return ticketService.findAll()
                .collectList()
                .map(ApiResponse::success);
    }

    @GetMapping("/{id}")
    public Mono<ApiResponse<?>> findById(@PathVariable Long id) {
        return ticketService.findById(id)
                .map(ApiResponse::success);
    }

    @PostMapping("/create")
    public Mono<ApiResponse<?>> create(@Valid @RequestBody TicketDto ticketDto) {
        return ticketService.create(ticketDto)
                .map(ApiResponse::created);
    }

    @PutMapping("/update/{id}")
    public Mono<ApiResponse<?>> update(@Valid @RequestBody TicketDto ticketDto) {
        return ticketService.update(ticketDto)
                .map(ApiResponse::updated);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ApiResponse<?>> delete(@PathVariable Long id) {
        return ticketService.delete(id)
                .thenReturn(ApiResponse.deleted("Deleted Success"));
    }

    @GetMapping
    public Mono<ApiResponse<PageResponse<?>>> findPagination(
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false) Priority priority
    ) {
        return ticketService.findPagination(
                        pageSize,
                        pageNumber,
                        search,
                        startDate,
                        endDate,
                        priority
                ).map(ApiResponse::success);
    }
}
