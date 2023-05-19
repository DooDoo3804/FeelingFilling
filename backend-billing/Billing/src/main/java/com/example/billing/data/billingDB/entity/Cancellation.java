package com.example.billing.data.billingDB.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Cancellation extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cancellationId;

    private String cancellationMethod;

    private int cancellationAmount;

    public Cancellation(String cancellationMethod, int cancellationAmount){
        this.cancellationMethod = cancellationMethod;
        this.cancellationAmount = cancellationAmount;
    }
}
