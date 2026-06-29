package com.ticketsystem.Mapper;

import com.ticketsystem.Dto.AttachmentDto;
import com.ticketsystem.Entities.Attachment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AttachmentMapper {
    AttachmentMapper INSTANCE = Mappers.getMapper(AttachmentMapper.class);
    AttachmentDto toDto(Attachment attachment);
    Attachment toEntity(AttachmentDto attachmentDto);
}
