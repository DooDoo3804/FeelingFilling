package com.example.billing.data.billingDB.entity;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "service+user_id")
    private int serviceUserId;
}
