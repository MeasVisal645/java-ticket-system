package com.ticketsystem.Mapper;

import com.ticketsystem.Dto.CategoryDto;
import com.ticketsystem.Entities.Category;

public class CategoryMapper {
    // Convert from Entity to DTO
    public static CategoryDto toDto(Category category) {

        if (category == null) {
            return null;
        }

        CategoryDto dto = new CategoryDto();

        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setIsActive(category.getIsActive());

        return dto;
    }

    // Convert from DTO to Entity
    public static Category toEntity(CategoryDto dto) {

        if (dto == null) {
            return null;
        }

        return Category.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .isActive(dto.getIsActive())
                .build();
    }
}
