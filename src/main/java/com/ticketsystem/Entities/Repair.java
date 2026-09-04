package com.ticketsystem.Entities;

import com.ticketsystem.Dto.RepairDto;
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
@Table("repair")
public class Repair {

    public static final String label =  "repair";
    public static final String ID_COLUMN  = "id";
    public static final String REPAIR_NO_COLUMN = "repairNo";
    public static final String TICKET_ID_COLUMN = "ticketId";
    public static final String ASSET_ID_COLUMN = "assetId";
    public static final String STATUS_COLUMN = "status";
    public static final String CREATED_BY_COLUMN = "createdBy";
    public static final String CREATED_AT_COLUMN = "createdAt";

    @Id
    @Column(ID_COLUMN)
    private Long id;
    @Column(REPAIR_NO_COLUMN)
    private String repairNo;
    @Column(TICKET_ID_COLUMN)
    private Long ticketId;
    @Column(ASSET_ID_COLUMN)
    private Long assetId;
    @Column(STATUS_COLUMN)
    private Status status;
    @Column(CREATED_BY_COLUMN)
    private String createdBy;
    @Column(CREATED_AT_COLUMN)
    private LocalDateTime createdAt;

    public static RepairBuilder from(RepairDto dto) {
        return Repair.builder()
                .id(dto.getId())
                .repairNo(dto.getRepairNo())
                .ticketId(dto.getTicketId())
                .assetId(dto.getAssetId());
    }
}
