package com.example.order.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.order.infrastructure.entity.OrderItemEntity;

public interface OrderItemJpaRepository extends JpaRepository<OrderItemEntity, Long> {
}
