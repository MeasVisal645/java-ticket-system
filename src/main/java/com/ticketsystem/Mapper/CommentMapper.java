package com.ticketsystem.Mapper;

import com.ticketsystem.Dto.CommentDto;
import com.ticketsystem.Dto.TicketDto;
import com.ticketsystem.Entities.Comment;
import com.ticketsystem.Entities.Ticket;

public class CommentMapper {
    // Convert from Entity to DTO
    public static CommentDto toDto(Comment comment) {

        if (comment == null) {
            return null;
        }

        CommentDto dto = new CommentDto();

        dto.setId(comment.getId());
        dto.setTicketId(comment.getTicketId());
        dto.setComment(comment.getComment());
        dto.setNote(comment.getNote());

        return dto;
    }

    // Convert from DTO to Entity
    public static Comment toEntity(CommentDto dto) {

        if (dto == null) {
            return null;
        }

        return Comment.builder()
                .id(dto.getId())
                .ticketId(dto.getTicketId())
                .comment(dto.getComment())
                .note(dto.getNote())
                .build();
    }
}
