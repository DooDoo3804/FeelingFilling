package com.example.billing.data.billingDB.repository;

import com.example.billing.data.billingDB.entity.Action;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionRepository extends JpaRepository<Action, Integer> {
}
