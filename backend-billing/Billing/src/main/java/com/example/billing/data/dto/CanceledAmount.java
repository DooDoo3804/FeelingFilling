package com.example.billing.data.dto;

import lombok.Data;

@Data
public class CanceledAmount {
    private int total;
    private int tax_free;
    private int vat;
    private int point;
    private int discount;
    private int green_deposit;
}
