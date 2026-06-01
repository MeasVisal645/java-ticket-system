package com.ticketsystem.Controller;

import com.ticketsystem.Dto.AttachmentDto;
import com.ticketsystem.Entities.Attachment;
import com.ticketsystem.Service.AttachmentService;
import com.ticketsystem.Utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class AttachmentController {

    private final AttachmentService attachmentService;

    @PostMapping(value = "ticket/{ticketId}/attachments",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<ResponseEntity<ApiResponse<Attachment>>> uploadAttachment(@PathVariable Long ticketId, @RequestPart("file") FilePart file) {
        Attachment attachment = new Attachment();
        attachment.setTicketId(ticketId);
        return attachmentService.create(attachment, Mono.just(file))
                .map(saved ->
                        ResponseEntity.status(HttpStatus.CREATED)
                                .body(new ApiResponse<>(
                                            HttpStatus.OK,
                                            "File uploaded successfully",
                                            saved
                                        )
                                )
                );
    }
}
