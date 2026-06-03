package com.ticketsystem.ServiceImpl;

import com.ticketsystem.Entities.Attachment;
import com.ticketsystem.Repository.AttachmentRepository;
import com.ticketsystem.Service.AttachmentService;
import com.ticketsystem.Service.FileService;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.AllArgsConstructor;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final Dotenv dotenv;
    private final FileService fileService;

    @Override
    public Mono<Attachment> create(Attachment attachment, Mono<FilePart> file) {
        attachment.setCreatedAt(LocalDateTime.now());
        return attachmentRepository.save(attachment)
                .flatMap(savedAttachment ->
                        file.flatMap(filePart -> {
                            String imageKey =
                                    savedAttachment.getId() + "-" + filePart.filename().trim().replaceAll("\\s+", "_");

                            return fileService.uploadFile(imageKey, filePart)
                                    .flatMap(filename -> {
                                        savedAttachment.setFileName(filename);
                                        savedAttachment.setFilePath("/uploads/" + filename);

                                        return attachmentRepository.save(savedAttachment);
                                    });
                        })
                );
    }
}
