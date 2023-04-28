package com.example.billing.data.dto;

import com.example.billing.data.billingDB.entity.User;
import lombok.Data;

@Data
public class UserDTO {
    private int userId;

    private String serviceName;

    private int serviceUserId;

    private String sid;

    private Long point;

    public UserDTO(User user){
        this.userId = user.getUserId();
        this.serviceName = user.getServiceName();
        this.serviceUserId = user.getServiceUserId();
        this.sid = user.getSid();
        this.point = user.getPoint();
    }
}
