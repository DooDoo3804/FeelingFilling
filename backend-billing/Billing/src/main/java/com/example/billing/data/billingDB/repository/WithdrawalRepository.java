package com.example.billing.data.billingDB.repository;

import com.example.billing.data.billingDB.entity.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawalRepository extends JpaRepository<Withdrawal, Integer> {
}
