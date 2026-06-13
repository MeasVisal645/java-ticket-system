package com.ticketsystem.Service;

import com.ticketsystem.Dto.AttachmentDto;
import com.ticketsystem.Entities.Attachment;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface AttachmentService {

    Mono<Attachment> findByTicketId(Long ticketId);
    Mono<Attachment> create(Attachment attachment, Mono<FilePart> file);
}
