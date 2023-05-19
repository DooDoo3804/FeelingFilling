package com.example.billing.data.dto;

import lombok.Data;

@Data
public class CancelDepositDTO {
    private String serviceName;
    private int serviceUserId;
    private int amount;
    private int orderId;
}
