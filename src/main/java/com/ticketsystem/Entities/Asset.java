package com.ticketsystem.Entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ticketsystem.Dto.AssetDto;
import com.ticketsystem.Utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("asset")
public class Asset {

    public static final String label = "Asset";
    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";
    public static final String TYPE_COLUMN = "type";
    public static final String CODE_COLUMN = "code";
    public static final String BRAND_COLUMN = "brand";
    public static final String PRICE_COLUMN = "price";
    public static final String VALUE_COLUMN = "value";
    public static final String PURCHASE_DATE_COLUMN = "purchaseDate";
    public static final String CONDITION_COLUMN = "condition";
    public static final String USER_COLUMN = "user";
    public static final String OTHER_COLUMN = "other";
    public static final String ASSET_TYPE_COLUMN = "assetType";
    public static final String IS_ACTIVE_COLUMN = "isActive";
    public static final String IS_DELETED_COLUMN = "isDeleted";
    public static final String TRADEMARK_ID_COLUMN = "tradeMarkId";
    public static final String OFFICE_ID_COLUMN = "officeId";
    public static final String CREATED_AT_COLUMN = "createdAt";
    public static final String UPDATED_AT_COLUMN = "updatedAt";

    @Id
    @Column(ID_COLUMN)
    private Long id;
    @Column(NAME_COLUMN)
    private String name;
    @Column(TYPE_COLUMN)
    private String type;
    @Column(CODE_COLUMN)
    private String code;
    @Column(BRAND_COLUMN)
    private String brand;
    @Column(PRICE_COLUMN)
    private BigDecimal price;
    @Column(VALUE_COLUMN)
    private BigDecimal value;
    @Column(PURCHASE_DATE_COLUMN)
    private LocalDate purchaseDate;
    @Column(CONDITION_COLUMN)
    private String condition;
    @Column(USER_COLUMN)
    private String user;
    @Column(OTHER_COLUMN)
    private String other;
    @Column(ASSET_TYPE_COLUMN)
    private String assetType;
    @Column(IS_ACTIVE_COLUMN)
    private Boolean active;
    @Column(IS_DELETED_COLUMN)
    private Boolean deleted;
    @Column(TRADEMARK_ID_COLUMN)
    private Long trademarkId;
    @Column(OFFICE_ID_COLUMN)
    private Long officeId;
    @Column(CREATED_AT_COLUMN)
    @JsonSerialize(using = DateUtils.class)
    private LocalDateTime createdAt;
    @Column(UPDATED_AT_COLUMN)
    @JsonSerialize(using = DateUtils.class)
    private LocalDateTime updatedAt;

    public static AssetBuilder from(AssetDto assetDto) {
        return Asset.builder()
                .id(assetDto.getId())
                .name(assetDto.getName())
                .type(assetDto.getType())
                .code(assetDto.getCode())
                .brand(assetDto.getBrand())
                .price(assetDto.getPrice())
                .value(assetDto.getValue())
                .purchaseDate(assetDto.getPurchaseDate())
                .condition(assetDto.getCondition())
                .user(assetDto.getUser())
                .other(assetDto.getOther())
                .assetType(assetDto.getAssetType())
                .trademarkId(assetDto.getTrademarkId())
                .officeId(assetDto.getOfficeId());
    }
}
