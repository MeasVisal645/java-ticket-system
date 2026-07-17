package com.ticketsystem.Repository;

import com.ticketsystem.Entities.Status;
import com.ticketsystem.Entities.Ticket;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface TicketRepository extends R2dbcRepository<Ticket, Long> {

    @Query("SELECT ticketNo FROM ticket ORDER BY id DESC LIMIT 1")
    Mono<String> findMaxTicketNo();

    @Query("SELECT COUNT(*) FROM ticket WHERE status = :status")
    Mono<Long> countByStatus(Status status);
}
