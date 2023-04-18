package com.example.billing.data.billingDB.repository;

import com.example.billing.data.billingDB.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Integer> {
}
