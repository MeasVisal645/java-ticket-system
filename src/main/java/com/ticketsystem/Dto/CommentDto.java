package com.ticketsystem.Dto;

import com.ticketsystem.Entities.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {

    private Long id;
    private Long ticketId;
    private String comment;
    private String note;
    private String createdBy;

    public static Comment update(Comment existing, CommentDto updated) {
        existing.setComment(updated.getComment());
        existing.setNote(updated.getNote());
        return existing;
    }
}
