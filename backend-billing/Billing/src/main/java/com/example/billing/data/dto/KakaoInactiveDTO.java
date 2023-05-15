package com.example.billing.data.dto;

import lombok.Data;

@Data
public class KakaoInactiveDTO {
    private String cid;
    private String sid;
    private String status;
    private String createdAt;
    private String inactivatedAt;
    private String lastApprovedAt;
}
