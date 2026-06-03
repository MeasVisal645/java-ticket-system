package com.ticketsystem.Controller;

import com.ticketsystem.Entities.Action;
import com.ticketsystem.Entities.History;
import com.ticketsystem.Entities.Priority;
import com.ticketsystem.Entities.Status;
import com.ticketsystem.Service.HistoryService;
import com.ticketsystem.Utils.ApiResponse;
import com.ticketsystem.Utils.PageResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/ticket-history")
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping("/all")
    public Mono<ApiResponse<?>> findAll() {
        return historyService.findAll()
                .collectList()
                .map(ApiResponse::success);
    }

    @GetMapping("/{id}")
    public Mono<ApiResponse<?>> findById(@PathVariable Long id) {
        return historyService.findById(id)
                .map(ApiResponse::success);
    }

    @GetMapping
    public Mono<ApiResponse<PageResponse<?>>> findPagination(
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false) String  search,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Priority priority,
            @RequestParam(required = false) Action action
    ) {
        return historyService.findPagination(
                        pageSize,
                        pageNumber,
                        startDate,
                        endDate,
                        search,
                        status,
                        priority,
                        action
                ).map(ApiResponse::success);
    }
}
