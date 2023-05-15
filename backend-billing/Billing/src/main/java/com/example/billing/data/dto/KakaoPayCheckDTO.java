package com.example.billing.data.dto;

import lombok.Data;

import java.util.Date;

@Data
public class KakaoPayCheckDTO {
    private boolean available;
    private String cid;
    private String sid;
    private String status;
    private Date createdAt;
    private Date lastApprovedAt;
    private Date inactivatedAt;
}
