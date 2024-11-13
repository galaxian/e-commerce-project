package com.example.order.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.order.domain.OrderItem;
import com.example.order.infrastructure.entity.OrderItemEntity;

public interface OrderItemJpaRepository extends JpaRepository<OrderItemEntity, Long> {
	List<OrderItemEntity> findByOrderEntityId(Long orderId);
}
