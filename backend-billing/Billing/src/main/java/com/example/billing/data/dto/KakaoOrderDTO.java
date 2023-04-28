package com.example.billing.data.dto;

import com.example.billing.data.billingDB.entity.Action;
import com.example.billing.data.billingDB.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class KakaoOrderDTO {
    private int orderId;

    private String tid;

    private User user;

    private List<Action> actions;
}
