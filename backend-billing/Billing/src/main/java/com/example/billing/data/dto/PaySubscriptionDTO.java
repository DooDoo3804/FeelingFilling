package com.example.billing.data.dto;

import lombok.Data;

@Data
public class PaySubscriptionDTO {

    private String serviceName;

    private int serviceUserId;

    private int amount;
}
