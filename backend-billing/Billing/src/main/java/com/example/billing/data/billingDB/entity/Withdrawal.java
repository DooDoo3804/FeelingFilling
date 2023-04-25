package com.example.billing.data.billingDB.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class Withdrawal extends BaseEntity{
    @Id
    private int withdrawalId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private String withdrawalMethod;

    private int withdrawalAmount;
}
