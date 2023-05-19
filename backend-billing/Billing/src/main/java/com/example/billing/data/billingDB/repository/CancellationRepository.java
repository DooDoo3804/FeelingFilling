package com.example.billing.data.billingDB.repository;

import com.example.billing.data.billingDB.entity.Cancellation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CancellationRepository extends JpaRepository<Cancellation, Integer> {
}
