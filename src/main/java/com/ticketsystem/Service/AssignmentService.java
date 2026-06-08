package com.ticketsystem.Service;

import com.ticketsystem.Dto.AssignmentsDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface AssignmentService {

    Mono<AssignmentsDto> assignTicket(Long ticketId, AssignmentsDto assignmentsDto);
}
