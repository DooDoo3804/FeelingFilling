package com.example.billing.data.dto;

import lombok.Data;

@Data
public class ServiceUserAndAmountDTO {

    private String serviceName;

    private int serviceUserId;

    private int amount;
}
