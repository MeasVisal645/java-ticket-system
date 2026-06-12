package com.ticketsystem.Controller;

import com.ticketsystem.Dto.AssignmentsDto;
import com.ticketsystem.Entities.Priority;
import com.ticketsystem.Service.AssignmentService;
import com.ticketsystem.Utils.ApiResponse;
import com.ticketsystem.Utils.PageResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/ticket-assignment")
@AllArgsConstructor
public class AssignmentController {

    private final AssignmentService  assignmentService;

    @GetMapping
    public Mono<ApiResponse<PageResponse<?>>> findPagination(
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false) Long assignTo
    ) {
        return assignmentService.findPagination(
                pageSize,
                pageNumber,
                startDate,
                endDate,
                assignTo
        ).map(ApiResponse::success);
    }

    @GetMapping("/my")
    public Mono<ApiResponse<PageResponse<?>>> findPaginationByAssignTo(
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate
    ) {
        return assignmentService.findPaginationByAssignTo(
                pageSize,
                pageNumber,
                startDate,
                endDate
        ).map(ApiResponse::success);
    }

    @PostMapping("/{id}/create")
    public Mono<ApiResponse<?>> create(@PathVariable(name = "id") Long ticketId, @RequestBody AssignmentsDto assignmentsDto) {
        return assignmentService.assignTicket(ticketId, assignmentsDto)
                .map(ApiResponse::success);
    }
}
