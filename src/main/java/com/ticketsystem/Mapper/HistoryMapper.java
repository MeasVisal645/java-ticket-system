package com.ticketsystem.Mapper;

import com.ticketsystem.Dto.HistoryDto;
import com.ticketsystem.Entities.History;

public class HistoryMapper {
    // Convert from Entity to DTO
    public static HistoryDto toDto(History history) {

        if (history == null) {
            return null;
        }

        HistoryDto dto = new HistoryDto();

        dto.setId(history.getId());
        dto.setTicketId(history.getTicketId());
        dto.setTicketNo(history.getTicketNo());
        dto.setSubject(history.getSubject());
        dto.setDescription(history.getDescription());
        dto.setPriority(history.getPriority());
        dto.setStatus(history.getStatus());
        dto.setAction(history.getAction());
        dto.setChangedAt(history.getChangedAt());

        return dto;
    }

    // Convert from DTO to Entity
    public static History toEntity(HistoryDto dto) {

        if (dto == null) {
            return null;
        }

        return History.builder()
                .id(dto.getId())
                .ticketId(dto.getTicketId())
                .subject(dto.getSubject())
                .description(dto.getDescription())
                .priority(dto.getPriority())
                .priority(dto.getPriority())
                .status(dto.getStatus())
                .action(dto.getAction())
                .changedAt(dto.getChangedAt())
                .build();
    }
}
