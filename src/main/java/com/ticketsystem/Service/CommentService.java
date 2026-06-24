package com.ticketsystem.Service;

import com.ticketsystem.Dto.CommentDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface CommentService {

    Mono<CommentDto> findById(Long id);
    Flux<CommentDto> findByTicketId(Long ticketId);
    Mono<CommentDto> create(CommentDto commentDto);
    Mono<CommentDto> update(CommentDto commentDto);
    Mono<Void> delete(Long id);
}
