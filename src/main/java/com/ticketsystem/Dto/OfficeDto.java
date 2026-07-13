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
    private Boolean isActive;
    private Long departmentId;

    public static Office update(Office existing, OfficeDto updated) {
        existing.setName(updated.getName());
        existing.setIsActive(updated.getIsActive());
        existing.setDepartmentId(updated.getDepartmentId());

        return existing;
    }
}
