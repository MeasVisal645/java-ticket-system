package com.ticketsystem.Mapper;

import com.ticketsystem.Dto.CommentDto;
import com.ticketsystem.Entities.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);
    CommentDto toDto(Comment comment);
}
