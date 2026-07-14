package com.ticketsystem.ServiceImpl;

import com.ticketsystem.Dto.AttachmentDto;
import com.ticketsystem.Entities.Attachment;
import com.ticketsystem.Mapper.AttachmentMapper;
import com.ticketsystem.Repository.AttachmentRepository;
import com.ticketsystem.Service.AttachmentService;
import com.ticketsystem.Service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final FileService fileService;

    @Override
    public Flux<Attachment> findByTicketId(Long ticketId) {
        return attachmentRepository.findByTicketId(ticketId);
    }

    @Override
    public Mono<AttachmentDto> create(Long ticketId, Mono<FilePart> file) {
        return file.flatMap(filePart -> {
                    String keyName = "tickets/" + ticketId + "/" + UUID.randomUUID() + "-" + filePart.filename();
                    return fileService.uploadFile(keyName, filePart)
                            .flatMap(url -> {
                                Attachment attachment = new Attachment();
                                attachment.setTicketId(ticketId);
                                attachment.setUrl(url);
                                attachment.setFileType(
                                        filePart.headers()
                                                .getContentType()
                                                .toString()
                                );
                                attachment.setCreatedAt(LocalDateTime.now());
                                return attachmentRepository.save(attachment);
                            });
                })
                .map(AttachmentMapper.INSTANCE::toDto);
    }
}
