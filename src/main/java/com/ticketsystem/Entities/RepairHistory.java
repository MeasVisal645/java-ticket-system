package com.ticketsystem.Entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ticketsystem.Dto.RepairHistoryDto;
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
@Table("repair-history")
public class RepairHistory {

    public static final String label = "repair-history";
    public static final String ID_COLUMN = "id";
    public static final String ASSET_ID_COLUMN = "assetId";
    public static final String TICKET_ID_COLUMN = "ticketId";
    public static final String OLD_PART_COLUMN = "oldPart";
    public static final String NEW_PART_COLUMN = "newPart";
    public static final String BRAND_COLUMN = "brand";
    public static final String PRICE_COLUMN = "price";
    public static final String DESCRIPTION_COLUMN = "description";
    public static final String BROKEN_DATE_COLUMN = "brokenDate";
    public static final String REPAIR_DATE_COLUMN = "repairDate";
    public static final String REASON_COLUMN = "reason";
    public static final String CREATED_AT_COLUMN = "createdAt";
    public static final String UPDATED_AT_COLUMN = "updatedAt";

    @Id
    @Column(ID_COLUMN)
    private Long id;
    @Column(ASSET_ID_COLUMN)
    private Long assetId;
    @Column(TICKET_ID_COLUMN)
    private Long ticketId;
    @Column(OLD_PART_COLUMN)
    private String oldPart;
    @Column(NEW_PART_COLUMN)
    private String newPart;
    @Column(BRAND_COLUMN)
    private String brand;
    @Column(PRICE_COLUMN)
    private String price;
    @Column(DESCRIPTION_COLUMN)
    private String description;
    @Column(REASON_COLUMN)
    private String reason;
    @Column(BROKEN_DATE_COLUMN)
    @JsonSerialize(using = DateUtils.class)
    private LocalDateTime brokenDate;
    @Column(REPAIR_DATE_COLUMN)
    @JsonSerialize(using = DateUtils.class)
    private LocalDateTime repairDate;
    @Column(CREATED_AT_COLUMN)
    @JsonSerialize(using = DateUtils.class)
    private LocalDateTime createdAt;
    @Column(CREATED_AT_COLUMN)
    @JsonSerialize(using = DateUtils.class)
    private LocalDateTime updatedAt;

    public static RepairHistory from(RepairHistoryDto dto) {
        return RepairHistory.builder()
                .id(dto.getId())
                .assetId(dto.getAssetId())
                .ticketId(dto.getTicketId())
                .oldPart(dto.getOldPart())
                .newPart(dto.getNewPart())
                .brand(dto.getBrand())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .reason(dto.getReason())
                .brokenDate(dto.getBrokenDate())
                .repairDate(dto.getRepairDate())
                .build();
    }
}
