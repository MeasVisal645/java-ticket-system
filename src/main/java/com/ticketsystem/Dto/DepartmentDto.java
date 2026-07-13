package com.ticketsystem.Dto;

import com.ticketsystem.Entities.Category;
import com.ticketsystem.Entities.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentDto {

    private Long id;
    private String name;
    private Boolean active;

    public static Department update(Department existing, DepartmentDto updated) {
        existing.setName(updated.getName());
        existing.setActive(updated.getActive());
        return existing;
    }
}
