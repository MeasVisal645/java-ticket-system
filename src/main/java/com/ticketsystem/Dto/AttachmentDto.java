package com.ticketsystem.Dto;

import com.ticketsystem.Entities.Attachment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttachmentDto {

    private Long id;
    private Long ticketId;
    private String fileName;
    private String filePath;
    private String contentType;
    private Long fileSize;

    public static Attachment update(Attachment existing, AttachmentDto updated) {
        existing.setTicketId(updated.getTicketId());
        existing.setFileName(updated.getFileName());
        existing.setFilePath(updated.getFilePath());
        return existing;
    }
}
