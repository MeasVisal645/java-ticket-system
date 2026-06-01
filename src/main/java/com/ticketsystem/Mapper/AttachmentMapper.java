package com.ticketsystem.Mapper;

import com.ticketsystem.Dto.AttachmentDto;
import com.ticketsystem.Entities.Attachment;

public class AttachmentMapper {
    // Convert from Entity to DTO
    public static AttachmentDto toDto(Attachment attachment) {

        if (attachment == null) {
            return null;
        }

        AttachmentDto dto = new AttachmentDto();

        dto.setId(attachment.getId());
        dto.setTicketId(attachment.getTicketId());
        dto.setFileName(attachment.getFileName());
        dto.setFilePath(attachment.getFilePath());

        return dto;
    }

    // Convert from DTO to Entity
    public static Attachment toEntity(AttachmentDto dto) {

        if (dto == null) {
            return null;
        }

        return Attachment.builder()
                .id(dto.getId())
                .ticketId(dto.getTicketId())
                .fileName(dto.getFileName())
                .filePath(dto.getFilePath())
                .build();
    }
}
