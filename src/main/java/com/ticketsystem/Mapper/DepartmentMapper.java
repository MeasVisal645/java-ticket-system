package com.ticketsystem.Mapper;

import com.ticketsystem.Dto.DepartmentDto;
import com.ticketsystem.Entities.Department;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);
    DepartmentDto toDto(Department department);
}
