package com.ticketsystem.Dto;

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
public class AssignmentsDto {

    private Long id;
    private Long ticketId;
    private Long assignBy;
    private Long assignTo;
    private LocalDateTime assignedAt;
}
