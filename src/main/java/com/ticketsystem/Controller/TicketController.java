package com.ticketsystem.Controller;

import com.ticketsystem.Dto.TicketDto;
import com.ticketsystem.Dto.UpdatePriorityRequest;
import com.ticketsystem.Dto.UpdateStatusRequest;
import com.ticketsystem.Entities.Priority;
import com.ticketsystem.Entities.Status;
import com.ticketsystem.Service.TicketService;
import com.ticketsystem.Utils.ApiResponse;
import com.ticketsystem.Utils.PageResponse;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Tag(name = "Tickets", description = "Ticket endpoints")
@RestController
@RequestMapping("/api/v1/ticket")
@AllArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @Hidden
    @Operation(summary = "Get all tickets", description = "Retrieve a list of all tickets")
    @GetMapping("/all")
    public Mono<ApiResponse<?>> findALl() {
        return ticketService.findAll()
                .collectList()
                .map(ApiResponse::success);
    }

    @Hidden
    @Operation(summary = "Get ticket by ID", description = "Retrieve details of a specific ticket")
    @GetMapping("/{id}")
    public Mono<ApiResponse<?>> findById(@PathVariable Long id) {
        return ticketService.findById(id)
                .map(ApiResponse::success);
    }

    @Operation(summary = "Create ticket", description = "Create a new ticket")
    @PostMapping("/create")
    public Mono<ApiResponse<?>> create(@Valid @RequestBody TicketDto ticketDto) {
        return ticketService.create(ticketDto)
                .map(ApiResponse::created);
    }

    @Operation(summary = "Update ticket", description = "Update an existing ticket")
    @PutMapping("/update/{id}")
    public Mono<ApiResponse<?>> update(@Valid @RequestBody TicketDto ticketDto) {
        return ticketService.update(ticketDto)
                .map(ApiResponse::updated);
    }

    @Operation(summary = "Delete ticket", description = "Delete an existing ticket")
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ApiResponse<?>> delete(@PathVariable Long id) {
        return ticketService.delete(id)
                .thenReturn(ApiResponse.deleted("Deleted Success"));
    }

    @Operation(summary = "Get paginated tickets", description = "Retrieve a paginated list of tickets")
    @GetMapping
    public Mono<ApiResponse<PageResponse<?>>> findPagination(
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false) Priority priority,
            @RequestParam(required = false) Status status
    ) {
        return ticketService.findPagination(
                        pageSize,
                        pageNumber,
                        search,
                        startDate,
                        endDate,
                        priority,
                        status
                ).map(ApiResponse::success);
    }

    @Hidden
    @Operation(summary = "Update ticket priority", description = "Update the priority of an existing ticket")
    @PatchMapping("/{id}/priority")
    public Mono<ApiResponse<?>> updatePriority(@PathVariable Long id, @RequestBody UpdatePriorityRequest request) {
        return ticketService.updatePriority(id, request.priority())
                .map(ApiResponse::updated);
    }

    @Hidden
    @Operation(summary = "Update ticket status", description = "Update the status of an existing ticket")
    @PatchMapping("/{id}/status")
    public Mono<ApiResponse<?>> updateStatus(@PathVariable Long id, @RequestBody UpdateStatusRequest request) {
        return ticketService.updateStatus(id, request.status())
                .map(ApiResponse::updated);
    }

    @Hidden
    @Operation(summary = "Count tickets", description = "Retrieve the total count of tickets")
    @GetMapping("/total")
    public Mono<ApiResponse<?>> count() {
        return ticketService.count()
                .map(ApiResponse::success);
    }

    @Hidden
    @Operation(summary = "Count tickets by status", description = "Retrieve the count of tickets based on their status")
    @GetMapping("/total/{status}")
    public Mono<ApiResponse<?>> countByStatus(@RequestParam Status status) {
        return ticketService.countByStatus(status)
                .map(ApiResponse::success);
    }
}
