package com.ticketsystem.Mapper;

import com.ticketsystem.Dto.CategoryDto;
import com.ticketsystem.Entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
    CategoryDto toDto(Category category);
}
