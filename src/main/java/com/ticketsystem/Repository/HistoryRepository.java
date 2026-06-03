package com.ticketsystem.Repository;

import com.ticketsystem.Entities.History;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface HistoryRepository extends R2dbcRepository<History,Long> {

    Mono<Void> deleteByTicketId(Long ticketId);
}
