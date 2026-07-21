package com.ticketsystem.Dto;

import com.ticketsystem.Entities.Action;
import com.ticketsystem.Entities.Priority;
import com.ticketsystem.Entities.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryResponseDto {

    private Long id;
    private String ticketNo;
    private Priority priority;
    private Priority oldPriority;
    private Status status;
    private Status oldStatus;
    private Action action;
}
