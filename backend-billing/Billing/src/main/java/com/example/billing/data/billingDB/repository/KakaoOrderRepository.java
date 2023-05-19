package com.example.billing.data.billingDB.repository;

import com.example.billing.data.billingDB.entity.KakaoOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KakaoOrderRepository extends JpaRepository<KakaoOrder, Integer> {
    KakaoOrder getKakaoOrderByOrderId(int orderId);
}
