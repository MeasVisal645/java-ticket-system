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
    public static final String FILE_TYPE_COLUMN = "fileType";
    public static final String URL_COLUMN = "url";
    public static final String KEY_NAME_COLUMN = "keyName";
    public static final String CREATED_AT_COLUMN = "createdAt";

    @Id
    @Column(ID_COLUMN)
    private Long id;
    @Column(TICKET_ID_COLUMN)
    private Long ticketId;
    @Column(FILE_TYPE_COLUMN)
    private String fileType;
    @Column(URL_COLUMN)
    private String url;
    @Column(KEY_NAME_COLUMN)
    private String keyName;
    @Column(CREATED_AT_COLUMN)
    @JsonSerialize(using = DateUtils.class)
    private LocalDateTime createdAt;

    public static AttachmentBuilder from(Attachment attachment) {
        return Attachment.builder()
                .id(attachment.getId())
                .ticketId(attachment.getTicketId())
                .fileType(attachment.getFileType())
                .url(attachment.getUrl())
                .keyName(attachment.getKeyName())
                .createdAt(attachment.getCreatedAt());
    }
}
