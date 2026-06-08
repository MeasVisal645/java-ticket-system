package com.ticketsystem.Controller;

import com.ticketsystem.Dto.AssignmentsDto;
import com.ticketsystem.Service.AssignmentService;
import com.ticketsystem.Utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/ticket-assignment")
@AllArgsConstructor
public class AssignmentController {

    private final AssignmentService  assignmentService;

    @PostMapping("/{id}/create")
    public Mono<ApiResponse<?>> create(@PathVariable(name = "id") Long ticketId, @RequestBody AssignmentsDto assignmentsDto) {
        return assignmentService.assignTicket(ticketId, assignmentsDto)
                .map(ApiResponse::success);
    }
}
