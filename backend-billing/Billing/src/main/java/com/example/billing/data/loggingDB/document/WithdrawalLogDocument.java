package com.example.billing.data.loggingDB.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Setter
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
