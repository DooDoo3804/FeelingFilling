package com.example.billing.data.billingDB.repository;

import com.example.billing.data.billingDB.entity.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositRepository extends JpaRepository<Deposit, Integer> {
}
