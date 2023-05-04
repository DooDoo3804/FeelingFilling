package com.example.billing.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProcessResultDTO {
    private boolean result;

    private String message;
}
