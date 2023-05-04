package com.example.billing.data.billingDB.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Withdrawal extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int withdrawalId;

    private String withdrawalMethod;

    private int withdrawalAmount;

    public Withdrawal(String withdrawalMethod, int withdrawalAmount){
        this.withdrawalMethod = withdrawalMethod;
        this.withdrawalAmount = withdrawalAmount;
    }
}
