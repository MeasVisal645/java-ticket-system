package com.ticketsystem.Entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ticketsystem.Dto.OfficeDto;
import com.ticketsystem.Utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("office")
public class Office {

    public static final String label = "Office";
    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";
    public static final String IS_ACTIVE_COLUMN = "isActive";
    public static final String DEPARTMENT_ID_COLUMN = "departmentId";
    public static final String CREATED_AT_COLUMN = "createdAt";
    public static final String UPDATED_AT_COLUMN = "updatedAt";

    @Id
    @Column(ID_COLUMN)
    private Long id;
    @Column(NAME_COLUMN)
    private String name;
    @Column(IS_ACTIVE_COLUMN)
    private Boolean isActive;
    @Column(DEPARTMENT_ID_COLUMN)
    private Long departmentId;
    @Column(CREATED_AT_COLUMN)
    @JsonSerialize(using = DateUtils.class)
    private LocalDateTime createdAt;
    @Column(UPDATED_AT_COLUMN)
    @JsonSerialize(using = DateUtils.class)
    private LocalDateTime updatedAt;

    public static OfficeBuilder from(OfficeDto officeDto) {
        return Office.builder()
                .id(officeDto.getId())
                .name(officeDto.getName())
                .isActive(officeDto.getIsActive())
                .departmentId(officeDto.getDepartmentId());
    }
}
