package com.example.billing.data.dto;

import lombok.Data;

@Data
public class KakaoInactiveDTO {
    private String cid;
    private String sid;
    private String status;
    private String created_at;
    private String inactivated_at;
    private String lastApproved_at;
}
