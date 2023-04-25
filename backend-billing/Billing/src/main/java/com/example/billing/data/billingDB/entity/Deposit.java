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
public class Deposit {

    @Id
    private int depositId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private String depositMethod;

    private int depositAmount;
}
