package com.example.billing.data.loggingDB.document;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Document(collection = "inactive")
@AllArgsConstructor
@Builder
public class InactiveLogDocumnet {
    @Id
    private String _id;
    private int userId;
    private String serviceName;
    private int serviceUserId;
    private String status;
    private String createdAt;
    private String inactivatedAt;
    private String lastApprovedAt;
    @CreatedDate
    private LocalDateTime createdDate;
}
