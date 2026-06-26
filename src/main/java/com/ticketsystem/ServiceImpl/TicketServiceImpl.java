package com.ticketsystem.ServiceImpl;

import com.ticketsystem.Dto.TicketDto;
import com.ticketsystem.Entities.Priority;
import com.ticketsystem.Entities.Status;
import com.ticketsystem.Entities.Ticket;
import com.ticketsystem.Mapper.TicketMapper;
import com.ticketsystem.Repository.AttachmentRepository;
import com.ticketsystem.Repository.CommentRepository;
import com.ticketsystem.Repository.TicketRepository;
import com.ticketsystem.Service.TicketService;
import com.ticketsystem.Utils.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final AttachmentRepository attachmentRepository;
    private final CommentRepository commentRepository;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final TicketNoGenerator  ticketNoGenerator;

    @Override
    public Flux<TicketDto> findAll() {
        return ticketRepository.findAll()
                .map(TicketMapper::toDto);
    }

    @Override
    public Mono<TicketDto> findById(Long id) {
        return ticketRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found")))
                .map(TicketMapper::toDto);
    }

    @Override
    public Mono<TicketDto> create(TicketDto ticketDto) {
        return ticketNoGenerator.generateTicketNo()
                .flatMap(ticketNo ->
                        ReactiveSecurityContextHolder.getContext()
                                .map(ctx -> ctx.getAuthentication().getName())
                                .flatMap(currentUser ->
                                        ticketRepository.save(
                                                Ticket.from(ticketDto)
                                                        .ticketNo(ticketNo)
                                                        .createdAt(LocalDateTime.now())
                                                        .createdBy(currentUser)
                                                        .status(Status.OPEN)
                                                        .build()
                                        )
                                )
                )
                .map(TicketMapper::toDto);
    }

    @Override
    public Mono<TicketDto> update(TicketDto ticketDto) {
        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> ctx.getAuthentication().getName())
                .flatMap(currentUser ->
                        ticketRepository.findById(ticketDto.getId())
                                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found")))
                                .flatMap(existing -> {
                                    TicketDto.update(existing, ticketDto);
                                    existing.setUpdatedAt(LocalDateTime.now());
                                    existing.setCreatedBy(currentUser);
                                    return ticketRepository.save(existing);
                                })
                                .map(TicketMapper::toDto)
                );
    }

    @Transactional
    @Override
    public Mono<Void> delete(Long id) {
        return ticketRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found")))
                .flatMap(ticket ->
                        attachmentRepository.deleteByTicketId(id)
                                .then(commentRepository.deleteByTicketId(id))
                                .then(ticketRepository.delete(ticket))
                );
    }

    @Override
    public Mono<PageResponse<TicketDto>> findPagination(Integer pageSize, Integer pageNumber, String search, LocalDateTime startDate, LocalDateTime endDate, Priority priority) {
        Criteria criteria = Criteria.empty();

        if (priority != null) {
            criteria = criteria.or(Criteria.where(Ticket.PRIORITY_COLUMN).is(priority));
        }

        if (startDate != null && endDate != null) {
            criteria = criteria.and(Ticket.CREATED_AT_COLUMN).between(startDate, endDate);
        }

        if (search != null && !search.isBlank()) {
            String pattern = "%" + search.trim() + "%";
            Criteria searchCriteria = Criteria.where(Ticket.TICKET_NO_COLUMN).like(pattern)
                    .or(Ticket.SUBJECT_COLUMN).like(pattern);
            criteria = criteria.and(searchCriteria);
        }

        return FilterPaginationUtils.fetchSimple(
                r2dbcEntityTemplate,
                Ticket.class,
                criteria,
                Optional.ofNullable(pageNumber).orElse(PaginationUtils.DEFAULT_PAGE_NUMBER),
                Optional.ofNullable(pageSize).orElse(PaginationUtils.DEFAULT_LIMIT),
                Sort.by(Sort.Order.desc(Ticket.CREATED_AT_COLUMN)),
                TicketMapper::toDto
        );
    }

    @Override
    @Transactional
    public Mono<TicketDto> updatePriority(Long id, Priority priority) {
        return ticketRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found")))
                .flatMap(existing -> {
                    existing.setPriority(priority);
                    existing.setUpdatedAt(LocalDateTime.now());
                    return ticketRepository.save(existing);
                })
                .map(TicketMapper::toDto);
    }

    @Override
    @Transactional
    public Mono<TicketDto> updateStatus(Long id, Status status) {
        return ticketRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found")))
                .flatMap(existing -> {
                    existing.setStatus(status);
                    existing.setUpdatedAt(LocalDateTime.now());
                    return ticketRepository.save(existing);
                })
                .map(TicketMapper::toDto);
    }


}
