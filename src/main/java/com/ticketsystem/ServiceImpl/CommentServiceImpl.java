package com.ticketsystem.ServiceImpl;

import com.ticketsystem.Dto.CommentDto;
import com.ticketsystem.Entities.Comment;
import com.ticketsystem.Mapper.CommentMapper;
import com.ticketsystem.Repository.CommentRepository;
import com.ticketsystem.Service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    @Override
    public Mono<CommentDto> findById(Long id) {
        return commentRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found")))
                .map(CommentMapper.INSTANCE::toDto);
    }

    @Override
    public Flux<CommentDto> findByTicketId(Long ticketId) {
        return commentRepository.findByTicketId(ticketId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found")))
                .map(CommentMapper.INSTANCE::toDto);
    }

    @Override
    public Mono<CommentDto> create(CommentDto commentDto) {
        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> ctx.getAuthentication().getName())
                .flatMap(currentUser ->
                        commentRepository.save(
                                Comment.from(commentDto)
                                    .createdBy(currentUser)
                                    .createdAt(LocalDateTime.now())
                                    .build())
                ).map(CommentMapper.INSTANCE::toDto);
    }

    @Override
    public Mono<CommentDto> update(CommentDto commentDto) {
        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> ctx.getAuthentication().getName())
                .flatMap(currentUser ->
                        commentRepository.findById(commentDto.getId())
                                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found")))
                                .flatMap(existing -> {
                                    if (!existing.getCreatedBy().equals(currentUser)) {
                                        return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only update your own comments"));
                                    }
                                    CommentDto.update(existing, commentDto);
                                    existing.setUpdatedAt(LocalDateTime.now());
                                    return commentRepository.save(existing);
                                })
                        )
                .map(CommentMapper.INSTANCE::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return commentRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found")))
                .flatMap(comment -> commentRepository.deleteById(id));
    }
}
