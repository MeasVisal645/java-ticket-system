package com.ticketsystem.Dto;

import com.ticketsystem.Entities.Priority;
import com.ticketsystem.Entities.Status;
import com.ticketsystem.Entities.Ticket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;

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
    private Priority priority;
    private String status;
    private String createdBy;

    public static Ticket update(Ticket existing, TicketDto updated) {
        existing.setTicketNo(updated.getTicketNo());
        existing.setCategoryId(updated.getCategoryId());
        existing.setSubject(updated.getSubject());
        existing.setDescription(updated.getDescription());
        existing.setPriority(updated.getPriority());
        existing.setStatus(Status.valueOf(updated.getStatus()));
        existing.setCreatedBy(updated.getCreatedBy());
        return existing;
    }
}
