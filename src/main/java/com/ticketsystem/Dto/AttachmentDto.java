package com.ticketsystem.Dto;

import com.ticketsystem.Entities.Attachment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttachmentDto {

    private Long id;
    private Long ticketId;
    private String fileType;
    private String url;

    public static Attachment update(Attachment existing, AttachmentDto updated) {
        existing.setTicketId(updated.getTicketId());
        existing.setFileType(updated.getFileType());
        existing.setUrl(updated.getUrl());
        return existing;
    }
}
