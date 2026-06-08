package com.ticketsystem.Entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ticketsystem.Dto.AssignmentsDto;
import com.ticketsystem.Utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("ticket_assignment")
public class Assignments {

    public static final String LABEL = "assignments";
    public static final String ID_COLUMN = "id";
    public static final String TICKET_ID_COLUMN = "ticketId";
    public static final String ASSIGN_BY_COLUMN = "assignBy";
    public static final String ASSIGN_TO_COLUMN = "assignTo";
    public static final String ASSIGNED_AT_COLUMN = "assignedAt";

    @Id
    @Column(ID_COLUMN)
    private Long id;
    @Column(TICKET_ID_COLUMN)
    private Long ticketId;
    @Column(ASSIGN_BY_COLUMN)
    private Long assignBy;
    @Column(ASSIGN_TO_COLUMN)
    private Long assignTo;
    @Column(ASSIGNED_AT_COLUMN)
    @JsonSerialize(using = DateUtils.class)
    private LocalDateTime assignedAt;

    public static AssignmentsBuilder from(AssignmentsDto assignmentsDto) {
        return Assignments.builder()
                .id(assignmentsDto.getId())
                .ticketId(assignmentsDto.getTicketId())
                .assignBy(assignmentsDto.getAssignBy())
                .assignTo(assignmentsDto.getAssignTo());
    }
}
