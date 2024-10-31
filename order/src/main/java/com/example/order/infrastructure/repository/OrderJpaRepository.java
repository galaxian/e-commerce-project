package com.example.order.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.order.infrastructure.entity.OrderEntity;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {
}
