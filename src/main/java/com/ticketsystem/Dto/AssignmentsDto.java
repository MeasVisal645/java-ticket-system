package com.ticketsystem.Dto;

import com.ticketsystem.Entities.Assignments;
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

    public static Assignments update(Assignments existing, AssignmentsDto updated) {
        existing.setAssignTo(updated.getAssignTo());
        return existing;
    }
}
