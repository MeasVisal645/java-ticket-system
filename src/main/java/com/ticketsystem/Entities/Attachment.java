package com.ticketsystem.Entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
@Table("ticket_attachment")
public class Attachment {

    public static final String LABEL = "attachment";
    public static final String ID_COLUMN = "id";
    public static final String TICKET_ID_COLUMN = "ticketId";
    public static final String FILE_NAME_COLUMN = "fileName";
    public static final String FILE_PATH_COLUMN = "filePath";
    public static final String CREATED_AT_COLUMN = "createdAt";

    @Id
    @Column(ID_COLUMN)
    private Long id;
    @Column(TICKET_ID_COLUMN)
    private Long ticketId;
    @Column(FILE_NAME_COLUMN)
    private String fileName;
    @Column(FILE_PATH_COLUMN)
    private String filePath;
    @Column(CREATED_AT_COLUMN)
    @JsonSerialize(using = DateUtils.class)
    private LocalDateTime createdAt;

    public static AttachmentBuilder from(Attachment attachment) {
        return Attachment.builder()
                .id(attachment.getId())
                .ticketId(attachment.getTicketId())
                .fileName(attachment.getFileName())
                .filePath(attachment.getFilePath())
                .createdAt(attachment.getCreatedAt());
    }
}
