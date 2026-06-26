package com.ticketsystem.Dto;

import com.ticketsystem.Entities.Priority;
import com.ticketsystem.Entities.Status;
import com.ticketsystem.Entities.Ticket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketDto {

    private Long id;
    private Long categoryId;
    private String ticketNo;
    private String subject;
    private String description;
    private String requestFrom;
    private Priority priority;
    private Status status;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Ticket update(Ticket existing, TicketDto updated) {
        existing.setTicketNo(updated.getTicketNo());
        existing.setCategoryId(updated.getCategoryId());
        existing.setSubject(updated.getSubject());
        existing.setDescription(updated.getDescription());
        existing.setRequestFrom(updated.getRequestFrom());
        existing.setPriority(updated.getPriority());
        existing.setStatus(updated.getStatus());
        existing.setCreatedBy(updated.getCreatedBy());
        existing.setUpdatedAt(updated.getUpdatedAt());
        return existing;
    }
}
