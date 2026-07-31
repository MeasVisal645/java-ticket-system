package com.ticketsystem.Dto;

import com.ticketsystem.Entities.RepairHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RepairHistoryDto {
    private Long id;
    private Long assetId;
    private Long ticketId;
    private String oldPart;
    private String newPart;
    private String brand;
    private String price;
    private String description;
    private String reason;
    private LocalDateTime brokenDate;
    private LocalDateTime repairDate;

    public static RepairHistory update(RepairHistory existing, RepairHistoryDto updated) {
        existing.setAssetId(updated.getAssetId());
        existing.setTicketId(updated.getTicketId());
        existing.setOldPart(updated.getOldPart());
        existing.setNewPart(updated.getNewPart());
        existing.setBrand(updated.getBrand());
        existing.setPrice(updated.getPrice());
        existing.setDescription(updated.getDescription());
        existing.setReason(updated.getReason());
        existing.setBrokenDate(updated.getBrokenDate());
        existing.setRepairDate(updated.getRepairDate());

        return existing;
    }
}
