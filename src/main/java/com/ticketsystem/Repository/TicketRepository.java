package com.ticketsystem.Repository;

import com.ticketsystem.Entities.Ticket;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends R2dbcRepository<Ticket, Long> {
}
