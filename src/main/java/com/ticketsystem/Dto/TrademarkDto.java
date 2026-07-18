package com.ticketsystem.Dto;

import com.ticketsystem.Entities.Trademark;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrademarkDto {
    private Long id;
    private String name;
    private String fullName;
    private Boolean isActive;

    public static Trademark update(Trademark existing, TrademarkDto updated) {
        existing.setName(updated.getName());
        existing.setFullName(updated.getFullName());
        existing.setIsActive(updated.getIsActive());

        return existing;
    }
}
