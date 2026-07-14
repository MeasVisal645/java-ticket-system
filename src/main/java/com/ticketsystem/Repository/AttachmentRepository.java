package com.ticketsystem.Repository;

import com.ticketsystem.Entities.Attachment;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AttachmentRepository extends R2dbcRepository<Attachment, Long> {

    Mono<Void> deleteByTicketId(Long ticketId);
    Flux<Attachment> findByTicketId(Long ticketId);
}
