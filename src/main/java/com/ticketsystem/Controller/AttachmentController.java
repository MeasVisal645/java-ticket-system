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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/ticket-attachments")
public class AttachmentController {

    private final AttachmentService attachmentService;

    @GetMapping("/{ticketId}")
    public Mono<ApiResponse<?>> findByTicketId(@PathVariable Long ticketId) {
        return attachmentService.findByTicketId(ticketId)
                .collectList()
                .map(ApiResponse::success);
    }

    @PostMapping(value = "/create/{ticketId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<ResponseEntity<ApiResponse<?>>> upload(
            @PathVariable Long ticketId,
            @RequestPart("file") Mono<FilePart> file
    ) {
        return attachmentService.create(ticketId, file)
                .map(data ->
                        ResponseEntity.status(HttpStatus.CREATED)
                                .body(new ApiResponse<>(
                                        HttpStatus.CREATED,
                                        "Uploaded successfully",
                                        data
                                ))
                );
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ApiResponse<?>> delete(@PathVariable Long id) {
        return attachmentService.delete(id)
                .thenReturn(ApiResponse.deleted("Deleted Success"));
    }
}
