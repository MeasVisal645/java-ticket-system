package com.ticketsystem.Mapper;

import com.ticketsystem.Dto.DepartmentDto;
import com.ticketsystem.Entities.Department;

public class DepartmentMapper {
    // Convert from Entity to DTO
    public static DepartmentDto toDto(Department department) {

        if (department == null) {
            return null;
        }

        DepartmentDto dto = new DepartmentDto();

        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setActive(department.isActive());

        return dto;
    }

    // Convert from DTO to Entity
    public static Department toEntity(DepartmentDto dto) {

        if (dto == null) {
            return null;
        }

        return Department.builder()
                .id(dto.getId())
                .name(dto.getName())
                .isActive(dto.isActive())
                .build();
    }
}
