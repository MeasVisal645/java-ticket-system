package com.ticketsystem.ServiceImpl;

import com.ticketsystem.Dto.AssignmentsDto;
import com.ticketsystem.Entities.Assignments;
import com.ticketsystem.Entities.Status;
import com.ticketsystem.Mapper.AssignmentMapper;
import com.ticketsystem.Repository.AssignmentRepository;
import com.ticketsystem.Service.AssignmentService;
import com.ticketsystem.Service.TicketService;
import com.ticketsystem.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final UserService userService;
    private final TicketService ticketService;

    @Override
    public Mono<AssignmentsDto> assignTicket(Long ticketId, AssignmentsDto assignmentsDto) {
        return userService.currentUser()
                .flatMap(currentUser -> {
                    Assignments assignment = AssignmentMapper.toEntity(assignmentsDto);
                    assignment.setTicketId(ticketId);
                    assignment.setAssignBy(currentUser.getId());
                    assignment.setAssignedAt(LocalDateTime.now());

                    return r2dbcEntityTemplate.insert(assignment)
                            .map(AssignmentMapper::toDto);
                })
                .flatMap(assignmentDto -> {
                        return ticketService.updateStatus(ticketId, Status.ASSIGNED)
                                .thenReturn(assignmentDto);
                });
    }
}
