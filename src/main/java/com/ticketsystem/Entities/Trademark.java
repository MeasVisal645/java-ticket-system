package com.ticketsystem.Entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ticketsystem.Dto.TrademarkDto;
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
@Table("trademark")
public class Trademark {

    public static final String label = "Trademark";
    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";
    public static final String FULLNAME_COLUMN = "fullName";
    public static final String IS_ACTIVE_COLUMN = "isActive";
    public static final String CREATED_AT_COLUMN = "createdAt";
    public static final String UPDATED_AT_COLUMN = "updatedAt";

    @Id
    @Column(ID_COLUMN)
    private Long id;
    @Column(NAME_COLUMN)
    private String name;
    @Column(FULLNAME_COLUMN)
    private String fullName;
    @Column(IS_ACTIVE_COLUMN)
    private boolean isActive;
    @Column(CREATED_AT_COLUMN)
    @JsonSerialize(using = DateUtils.class)
    private LocalDateTime createdAt;
    @Column(UPDATED_AT_COLUMN)
    @JsonSerialize(using = DateUtils.class)
    private LocalDateTime updatedAt;

    public static TrademarkBuilder from(TrademarkDto trademarkDto) {
        return Trademark.builder()
                .id(trademarkDto.getId())
                .name(trademarkDto.getName())
                .fullName(trademarkDto.getFullName())
                .isActive(trademarkDto.isActive());
    }
}
