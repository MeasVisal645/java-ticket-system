package com.ticketsystem.Dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ticketsystem.Entities.Repair;
import com.ticketsystem.Utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RepairDto {

    private Long id;
    private String repairNo;
    private Long assetId;
    private Long ticketId;
    @JsonSerialize(using = DateUtils.class)
    private LocalDateTime createdAt;

    public static Repair update(RepairDto repairDto, Repair repair) {
        repair.setId(repairDto.getId());
        repair.setAssetId(repairDto.getAssetId());
        repair.setTicketId(repairDto.getTicketId());
        repair.setCreatedAt(repairDto.getCreatedAt());
        return repair;
    }
}
