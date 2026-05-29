package com.ticketsystem.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("category")
public class Category {

    public static final String LABEL = "category";
    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";
    public static final String DESCRIPTION_COLUMN = "description";
    public static final String IS_ACTIVE_COLUMN = "isActive";

    @Id
    @Column(ID_COLUMN)
    private Long id;
    @Column(NAME_COLUMN)
    private String name;
    @Column(DESCRIPTION_COLUMN)
    private String description;
    @Column(IS_ACTIVE_COLUMN)
    private boolean isActive;

    public static CategoryBuilder from(Category category) {
        return Category.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .isActive(category.isActive());
    }

    public static Category update(Category existing, Category updated) {
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setActive(updated.isActive());
        return existing;
    }
}
