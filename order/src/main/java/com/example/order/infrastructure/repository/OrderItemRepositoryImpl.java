package com.example.order.infrastructure.repository;

import org.springframework.stereotype.Repository;

import com.example.order.application.port.out.OrderItemRepository;
import com.example.order.domain.OrderItem;
import com.example.order.infrastructure.entity.OrderItemEntity;

@Repository
public class OrderItemRepositoryImpl implements OrderItemRepository {

	private final OrderItemJpaRepository orderItemJpaRepository;

	public OrderItemRepositoryImpl(OrderItemJpaRepository orderItemJpaRepository) {
		this.orderItemJpaRepository = orderItemJpaRepository;
	}

	@Override
	public OrderItem save(OrderItem orderItem) {
		return orderItemJpaRepository.save(OrderItemEntity.from(orderItem)).toDomain();
	}
}
