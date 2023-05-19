package com.example.billing.data.dto;

import lombok.Data;

import java.util.Date;

@Data
public class KakaoPayCheckDTO {
    private boolean available;
    private String cid;
    private String sid;
    private String status;
    private Date created_at;
    private Date last_approved_at;
    private Date inactivated_at;
}
