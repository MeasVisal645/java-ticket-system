package com.ticketsystem.Service;

import com.ticketsystem.Dto.CommentDto;
import com.ticketsystem.Entities.Comment;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface CommentService {

    Mono<CommentDto> findById(Long id);
    Mono<CommentDto> create(CommentDto commentDto);
    Mono<CommentDto> update(CommentDto commentDto);
    Mono<Void> delete(Long id);
}
