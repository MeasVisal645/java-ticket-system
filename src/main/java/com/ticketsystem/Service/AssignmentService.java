package com.ticketsystem.Service;

import com.ticketsystem.Dto.AssignmentsDto;
import com.ticketsystem.Utils.PageResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public interface AssignmentService {

    Mono<PageResponse<AssignmentsDto>> findPagination(Integer pageSize, Integer pageNumber, LocalDateTime startDate, LocalDateTime endDate, Long assignTo);
    Mono<PageResponse<AssignmentsDto>> findPaginationByAssignTo(Integer pageSize, Integer pageNumber, LocalDateTime startDate, LocalDateTime endDate);
    Mono<AssignmentsDto> assignTicket(Long ticketId, AssignmentsDto assignmentsDto);
}
