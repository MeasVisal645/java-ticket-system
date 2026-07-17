package com.ticketsystem.Service;

import com.ticketsystem.Dto.TicketDto;
import com.ticketsystem.Dto.UpdateStatusRequest;
import com.ticketsystem.Entities.Priority;
import com.ticketsystem.Entities.Status;
import com.ticketsystem.Entities.Ticket;
import com.ticketsystem.Utils.ApiResponse;
import com.ticketsystem.Utils.PageResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public interface TicketService {

    Flux<TicketDto> findAll();
    Mono<TicketDto> findById(Long id);
    Mono<TicketDto> create(TicketDto ticketDto);
    Mono<TicketDto> update(TicketDto ticketDto);
    Mono<Void> delete(Long id);

    Mono<PageResponse<TicketDto>> findPagination(Integer pageNumber, Integer pageSize, String search, LocalDateTime startDate, LocalDateTime endDate, Priority priority, Status status);
    Mono<TicketDto> updatePriority(Long id, Priority priority);
    Mono<TicketDto> updateStatus(Long id, Status status);

    Mono<Map<String, Long>> count();
    Mono<Map<String, Long>> countByStatus(Status status);
}
