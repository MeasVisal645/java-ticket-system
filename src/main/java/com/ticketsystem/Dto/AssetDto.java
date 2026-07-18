package com.ticketsystem.Dto;

import com.ticketsystem.Entities.Asset;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssetDto {

    private Long id;
    private String name;
    private String type;
    private String code;
    private String brand;
    private BigDecimal price;
    private BigDecimal value;
    private LocalDate purchaseDate;
    private String condition;
    private String user;
    private String other;
    private String assetType;
    private Boolean active;
    private Boolean deleted;
    private Long trademarkId;
    private Long officeId;

    public static Asset update(Asset existing, AssetDto updated) {
        existing.setName(updated.getName());
        existing.setType(updated.getType());
        existing.setCode(updated.getCode());
        existing.setBrand(updated.getBrand());
        existing.setPrice(updated.getPrice());
        existing.setPrice(updated.getPrice());
        existing.setPurchaseDate(updated.getPurchaseDate());
        existing.setCondition(updated.getCondition());
        existing.setUser(updated.getUser());
        existing.setOther(updated.getOther());
        existing.setAssetType(updated.getAssetType());
        existing.setTrademarkId(updated.getTrademarkId());
        existing.setOfficeId(updated.getOfficeId());
        existing.setActive(updated.getActive());
        existing.setDeleted(updated.getDeleted());

        return existing;
    }
}
