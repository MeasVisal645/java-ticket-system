package com.ticketsystem.Repository;

import com.ticketsystem.Entities.Comment;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CommentRepository extends R2dbcRepository<Comment, Long> {

    Mono<Void> deleteByTicketId(Long ticketId);
}
