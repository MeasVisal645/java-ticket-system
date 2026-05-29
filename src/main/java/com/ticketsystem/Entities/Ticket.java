package com.ticketsystem.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ticketsystem.Dto.TicketDto;
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
@Table("ticket")
public class Ticket {

    public static final String LABEL = "ticket";
    public static final String ID_COLUMN = "id";
    public static final String CATEGORY_ID_COLUMN = "categoryId";
    public static final String TICKET_NO_COLUMN = "ticketNo";
    public static final String SUBJECT_COLUMN = "subject";
    public static final String DESCRIPTION_COLUMN = "description";
    public static final String PRIORITY_COLUMN = "priority";
    public static final String STATUS_COLUMN = "status";
    public static final String CREATED_AT_COLUMN = "createdAt";
    public static final String UPDATED_AT_COLUMN = "updatedAt";

    @Id
    @Column(ID_COLUMN)
    private Long id;
    @Column(CATEGORY_ID_COLUMN)
    private Long categoryId;
    @Column(TICKET_NO_COLUMN)
    private String ticketNo;
    @Column(SUBJECT_COLUMN)
    private String subject;
    @Column(DESCRIPTION_COLUMN)
    private String description;
    @Column(PRIORITY_COLUMN)
    private Priority priority;
    @Column(STATUS_COLUMN)
    private String status;
    @Column(CREATED_AT_COLUMN)
    @JsonSerialize(using = DateUtils.class)
    private LocalDateTime createdAt;
    @Column(UPDATED_AT_COLUMN)
    @JsonSerialize(using = DateUtils.class)
    private LocalDateTime updatedAt;

    public static TicketBuilder from(TicketDto ticketDto) {
        return Ticket.builder()
                .id(ticketDto.getId())
                .categoryId(ticketDto.getCategoryId())
                .ticketNo(ticketDto.getTicketNo())
                .subject(ticketDto.getSubject())
                .description(ticketDto.getDescription())
                .priority(ticketDto.getPriority())
                .status(ticketDto.getStatus());
    }

}
