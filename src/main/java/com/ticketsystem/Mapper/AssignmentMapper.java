package com.ticketsystem.Mapper;

import com.ticketsystem.Dto.AssignmentsDto;
import com.ticketsystem.Entities.Assignments;

public class AssignmentMapper {
    // Convert from Entity to DTO
    public static AssignmentsDto toDto(Assignments assignments) {

        if (assignments == null) {
            return null;
        }

        AssignmentsDto dto = new AssignmentsDto();

        dto.setId(assignments.getId());
        dto.setTicketId(assignments.getTicketId());
        dto.setAssignBy(assignments.getAssignBy());
        dto.setAssignTo(assignments.getAssignTo());
        dto.setAssignedAt(assignments.getAssignedAt());

        return dto;
    }

    // Convert from DTO to Entity
    public static Assignments toEntity(AssignmentsDto dto) {

        if (dto == null) {
            return null;
        }

        return Assignments.builder()
                .id(dto.getId())
                .ticketId(dto.getTicketId())
                .assignBy(dto.getAssignBy())
                .assignTo(dto.getAssignTo())
                .assignedAt(dto.getAssignedAt())
                .build();
    }
}
