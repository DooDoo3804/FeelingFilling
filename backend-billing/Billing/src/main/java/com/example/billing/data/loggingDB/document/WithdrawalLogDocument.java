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
@Document(collection = "withdrawal")
@AllArgsConstructor
@Builder
public class WithdrawalLogDocument {
    @Id
    private String _id;
    private int userId;
    private String serviceName;
    private int serviceUserId;
    private long balance;
    private String status;
    private int withdrawalId;
    private String withdrawalMethod;
    private int withdrawalAmount;
    @CreatedDate
    private LocalDateTime createdDate;
}
