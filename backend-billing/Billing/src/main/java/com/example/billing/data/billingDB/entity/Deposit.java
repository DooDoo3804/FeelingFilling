package com.example.billing.data.billingDB.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Deposit extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int depositId;

    private String depositMethod;

    private int depositAmount;

    public Deposit(String depositMethod, int depositAmount){
        this.depositMethod = depositMethod;
        this.depositAmount  = depositAmount;
    }
}
