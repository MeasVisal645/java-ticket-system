package com.ticketsystem.Dto;

import com.ticketsystem.Entities.Action;
import com.ticketsystem.Entities.Priority;
import com.ticketsystem.Entities.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryDto {

    private Long id;
    private Long ticketId;
    private String ticketNo;
    private String subject;
    private String description;
    private Priority priority;
    private Status status;
    private Action action;
    private LocalDateTime changedAt;
}
