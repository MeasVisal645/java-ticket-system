package com.ticketsystem.Service;

import com.ticketsystem.Dto.AttachmentDto;
import com.ticketsystem.Entities.Attachment;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface AttachmentService {

    Flux<AttachmentDto> findByTicketId(Long ticketId);
    Mono<AttachmentDto> create(Long ticketId, Mono<FilePart> file);
    Mono<Void> delete(Long id);
}
