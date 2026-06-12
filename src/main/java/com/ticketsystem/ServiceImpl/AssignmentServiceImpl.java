package com.ticketsystem.ServiceImpl;

import com.ticketsystem.Dto.AssignmentsDto;
import com.ticketsystem.Entities.Assignments;
import com.ticketsystem.Entities.Status;
import com.ticketsystem.Entities.Ticket;
import com.ticketsystem.Mapper.AssignmentMapper;
import com.ticketsystem.Repository.AssignmentRepository;
import com.ticketsystem.Service.AssignmentService;
import com.ticketsystem.Service.TicketService;
import com.ticketsystem.Service.UserService;
import com.ticketsystem.Utils.FilterPaginationUtils;
import com.ticketsystem.Utils.PageResponse;
import com.ticketsystem.Utils.PaginationUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final UserService userService;
    private final TicketService ticketService;

    @Override
    public Mono<PageResponse<AssignmentsDto>> findPagination(Integer pageSize, Integer pageNumber, LocalDateTime startDate, LocalDateTime endDate, Long assignTo) {
        Criteria criteria = Criteria.empty();

        if (assignTo != null) {
            criteria = criteria.and(Assignments.ASSIGN_TO_COLUMN)
                    .is(assignTo);
        }

        if (startDate != null && endDate != null) {
            criteria = criteria.and(Assignments.ASSIGNED_AT_COLUMN).between(startDate, endDate);
        }

        return FilterPaginationUtils.fetchSimple(
                r2dbcEntityTemplate,
                Assignments.class,
                criteria,
                Optional.ofNullable(pageNumber).orElse(PaginationUtils.DEFAULT_PAGE_NUMBER),
                Optional.ofNullable(pageSize).orElse(PaginationUtils.DEFAULT_LIMIT),
                Sort.by(Sort.Order.desc(Assignments.ASSIGNED_AT_COLUMN)),
                AssignmentMapper::toDto
        );
    }

    @Override
    public Mono<PageResponse<AssignmentsDto>> findPaginationByAssignTo(Integer pageSize, Integer pageNumber, LocalDateTime startDate, LocalDateTime endDate) {
        return userService.currentUser()
                .flatMap(currentUser -> 
                        findPagination(
                                pageSize, 
                                pageNumber, 
                                startDate, 
                                endDate, 
                                currentUser.getId()
                        )
                );
    }

    @Override
    public Mono<AssignmentsDto> assignTicket(Long ticketId, AssignmentsDto assignmentsDto) {
        return ticketService.findById(ticketId)
                .flatMap(ticket -> {
                    if (ticket.getStatus() == Status.ASSIGNED) {
                        return Mono.error(new ResponseStatusException(HttpStatus.CONFLICT, "Ticket is already assigned"));
                    }

                    return userService.currentUser()
                            .flatMap(currentUser -> {
                                Assignments assignment = AssignmentMapper.toEntity(assignmentsDto);
                                assignment.setTicketId(ticketId);
                                assignment.setAssignBy(currentUser.getId());
                                assignment.setAssignedAt(LocalDateTime.now());

                                return r2dbcEntityTemplate.insert(assignment)
                                        .map(AssignmentMapper::toDto);
                            })
                            .flatMap(assignmentDto ->
                                    ticketService.updateStatus(ticketId, Status.ASSIGNED)
                                            .thenReturn(assignmentDto)
                            );
                });
    }
}
