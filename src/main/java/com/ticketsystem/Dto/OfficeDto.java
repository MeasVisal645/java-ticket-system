package com.ticketsystem.Dto;

import com.ticketsystem.Entities.Office;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfficeDto {

    private Long id;
    private String name;
    private boolean active;
    private Long departmentId;

    public static Office update(Office existing, OfficeDto updated) {
        existing.setName(updated.getName());
        existing.setActive(updated.isActive());
        existing.setDepartmentId(updated.getDepartmentId());

        return existing;
    }
}
