package com.example.billing.data.loggingDB.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Setter
@NoArgsConstructor
@Document(collection = "deposit")
@AllArgsConstructor
@Builder
public class DepositDocument {
    @Id
    private String _id;
    private int userId;
    private String serviceName;
    private int serviceUserId;
    private String status;
    private int orderId;
    private String tid;
    private String sid;
    private String aid;
    private int depositId;
    private String depositMethod;
    private int depositAmount;
    private LocalDateTime createdDate;

}
