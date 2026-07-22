package com.ticketsystem.Dto;

import com.ticketsystem.Entities.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {

    private Long id;
    private String name;
    private String description;
    private boolean isActive;

    public static Category update(Category existing, CategoryDto updated) {
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setActive(updated.isActive());

        return existing;
    }
}
