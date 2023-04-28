package com.example.billing.data.billingDB.entity;

import lombok.*;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    private String serviceName;

    private int serviceUserId;

    private String sid;

    private long point;

    @OneToMany
    @JoinColumn(name = "userId")
    private List<Deposit> deposit = new LinkedList<>();

    @OneToMany
    @JoinColumn(name = "userId")
    private List<Withdrawal> withdrawals = new LinkedList<>();
}
