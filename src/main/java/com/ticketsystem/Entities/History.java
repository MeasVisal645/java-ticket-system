package com.ticketsystem.Entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
@Table("ticket_history")
public class History {

    public static final String LABEL = "history";
    public static final String ID_COLUMN = "id";
    public static final String TICKET_ID_COLUMN = "ticketId";
    public static final String CATEGORY_ID_COLUMN = "categoryId";
    public static final String TICKET_NO_COLUMN = "ticketNo";
    public static final String SUBJECT_COLUMN = "subject";
    public static final String DESCRIPTION_COLUMN = "description";
    public static final String PRIORITY_COLUMN = "priority";
    public static final String STATUS_COLUMN = "status";
    public static final String OLD_PRIORITY_COLUMN = "old_priority";
    public static final String OLD_STATUS_COLUMN = "old_status";
    public static final String CREATED_AT_COLUMN = "createdAt";
    public static final String UPDATED_AT_COLUMN = "updatedAt";
    public static final String ACTION_COLUMN = "action";
    public static final String CHANGED_AT_COLUMN = "changedAt";

    @Id
    @Column(ID_COLUMN)
    private Long id;
    @Column(TICKET_ID_COLUMN)
    private Long ticketId;
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
    @Column(OLD_PRIORITY_COLUMN)
    private Priority oldPriority;
    @Column(STATUS_COLUMN)
    private Status status;
    @Column(OLD_STATUS_COLUMN)
    private Status oldStatus;
    @Column(CREATED_AT_COLUMN)
    @JsonSerialize(using = DateUtils.class)
    private LocalDateTime createdAt;
    @Column(UPDATED_AT_COLUMN)
    @JsonSerialize(using = DateUtils.class)
    private LocalDateTime updatedAt;
    @Column(ACTION_COLUMN)
    private Action action;
    @Column(CHANGED_AT_COLUMN)
    @JsonSerialize(using = DateUtils.class)
    private LocalDateTime changedAt;
}
