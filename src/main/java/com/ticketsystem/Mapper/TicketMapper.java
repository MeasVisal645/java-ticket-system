package com.ticketsystem.Mapper;

import com.ticketsystem.Dto.TicketDto;
import com.ticketsystem.Entities.Status;
import com.ticketsystem.Entities.Ticket;

public class TicketMapper {

    // Convert from Entity to DTO
    public static TicketDto toDto(Ticket ticket) {

        if (ticket == null) {
            return null;
        }

        TicketDto dto = new TicketDto();

        dto.setId(ticket.getId());
        dto.setCategoryId(ticket.getCategoryId());
        dto.setTicketNo(ticket.getTicketNo());
        dto.setSubject(ticket.getSubject());
        dto.setDescription(ticket.getDescription());
        dto.setPriority(ticket.getPriority());
        dto.setStatus(ticket.getStatus());
        dto.setCreatedBy(ticket.getCreatedBy());
        dto.setCreatedAt(ticket.getCreatedAt());

        return dto;
    }

    // Convert from DTO to Entity
    public static Ticket toEntity(TicketDto dto) {

        if (dto == null) {
            return null;
        }

        return Ticket.builder()
                .id(dto.getId())
                .categoryId(dto.getCategoryId())
                .ticketNo(dto.getTicketNo())
                .subject(dto.getSubject())
                .description(dto.getDescription())
                .priority(dto.getPriority())
                .status(dto.getStatus())
                .createdBy(dto.getCreatedBy())
                .createdAt(dto.getCreatedAt())
                .build();
    }
}
