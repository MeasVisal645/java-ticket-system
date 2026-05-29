package com.ticketsystem.Entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ticketsystem.Dto.CommentDto;
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
@Table("ticket_comment")
public class Comment {

    public static final String LABEL = "comment";
    public static final String ID_COLUMN = "id";
    public static final String TICKET_ID_COLUMN = "ticketId";
    public static final String COMMENT_COLUMN = "comment";
    public static final String NOTE_COLUMN = "note";
    public static final String CREATED_AT_COLUMN = "createdAt";

    @Id
    @Column(ID_COLUMN)
    private Long id;
    @Column(TICKET_ID_COLUMN)
    private Long ticketId;
    @Column(COMMENT_COLUMN)
    private String comment;
    @Column(NOTE_COLUMN)
    private String note;
    @Column(CREATED_AT_COLUMN)
    @JsonSerialize(using = DateUtils.class)
    private LocalDateTime createdAt;

    public static CommentBuilder from(CommentDto commentDto) {
        return Comment.builder()
                .id(commentDto.getId())
                .ticketId(commentDto.getTicketId())
                .comment(commentDto.getComment())
                .note(commentDto.getNote());
    }


}
