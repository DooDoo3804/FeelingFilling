package com.example.billing.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WithdrawalDTO {
    private boolean isSuccess;
    private long requestedAmount;
    private long balanceBefore;
    private long balanceAfter;
    private String message;
}
