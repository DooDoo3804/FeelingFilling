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

    private String withdrawalMethod;

    private int withdrawalAmount;

    public Withdrawal(String withdrawalMethod, int withdrawalAmount){
        this.withdrawalMethod = withdrawalMethod;
        this.withdrawalAmount = withdrawalAmount;
    }
}
