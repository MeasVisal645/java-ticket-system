package com.ticketsystem.Entities;

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
    public static final String TICKET_ID_COLUMN = "ticket_id";
    public static final String ASSET_ID_COLUMN = "asset_id";
    public static final String CREATED_AT_COLUMN = "created_at";

    @Id
    @Column(ID_COLUMN)
    private Long id;
    @Column(TICKET_ID_COLUMN)
    private Long ticketId;
    @Column(ASSET_ID_COLUMN)
    private Long assetId;
    @Column(CREATED_AT_COLUMN)
    private LocalDateTime createdAt;
}
