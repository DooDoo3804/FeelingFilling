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
public class Deposit extends BaseEntity{

    @Id
    private int depositId;

    private String depositMethod;

    private int depositAmount;

    public Deposit(String depositMethod, int depositAmount){
        this.depositMethod = depositMethod;
        this.depositAmount  = depositAmount;
    }
}
