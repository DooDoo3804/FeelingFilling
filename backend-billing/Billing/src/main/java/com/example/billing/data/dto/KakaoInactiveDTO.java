package com.example.billing.data.dto;

import lombok.Data;

@Data
public class KakaoInactiveDTO {
    String cid;
    String sid;
    String status;
    String createdAt;
    String inactivatedAt;
    String lastApprovedAt;
}
