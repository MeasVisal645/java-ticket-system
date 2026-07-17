package com.ticketsystem.ServiceImpl;

import com.ticketsystem.Dto.AttachmentDto;
import com.ticketsystem.Entities.Attachment;
import com.ticketsystem.Mapper.AttachmentMapper;
import com.ticketsystem.Repository.AttachmentRepository;
import com.ticketsystem.Service.AttachmentService;
import com.ticketsystem.Service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
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
    public Flux<AttachmentDto> findByTicketId(Long ticketId) {
        return attachmentRepository.findByTicketId(ticketId)
                .map(AttachmentMapper.INSTANCE::toDto);
    }

    @Override
    public Mono<AttachmentDto> create(Long ticketId, Mono<FilePart> file) {
        return file.flatMap(filePart -> {
                    String keyName = "attachments/" + ticketId + "/" + UUID.randomUUID();
                    return fileService.uploadFile(keyName, filePart)
                            .flatMap(url -> {
                                Attachment attachment = new Attachment();
                                attachment.setTicketId(ticketId);
                                attachment.setUrl(url);
                                attachment.setKeyName(keyName);
                                attachment.setFileType(
                                        filePart.headers()
                                                .getContentType()
                                                .toString()
                                );
                                attachment.setCreatedAt(LocalDateTime.now());
                                return attachmentRepository.save(attachment);
                            })
                            .onErrorResume(e ->
                                    fileService.deleteFile(keyName)
                                            .then(Mono.error(e)));
                })
                .map(AttachmentMapper.INSTANCE::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return attachmentRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Attachment not found")))
                .flatMap(attachment -> {
                    String keyName = attachment.getKeyName();

                    Mono<Void> deleteFile = (keyName == null || keyName.isBlank())
                            ? Mono.empty()
                            : fileService.deleteFile(keyName).onErrorResume(e -> Mono.empty());

                    return deleteFile
                            .then(attachmentRepository.deleteById(id));
                });
    }
}
