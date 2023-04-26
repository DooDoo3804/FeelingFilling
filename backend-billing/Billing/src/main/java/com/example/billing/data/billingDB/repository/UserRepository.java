package com.example.billing.data.billingDB.repository;

import com.example.billing.data.billingDB.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
