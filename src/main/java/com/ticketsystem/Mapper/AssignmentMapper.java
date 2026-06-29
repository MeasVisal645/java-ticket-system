package com.ticketsystem.Mapper;

import com.ticketsystem.Dto.AssignmentsDto;
import com.ticketsystem.Entities.Assignments;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {
    AssignmentMapper INSTANCE = Mappers.getMapper(AssignmentMapper.class);
    AssignmentsDto toDto(Assignments assignments);
    Assignments toEntity(AssignmentsDto assignmentsDto);
}
