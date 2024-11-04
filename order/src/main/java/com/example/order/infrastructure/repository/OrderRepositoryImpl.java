package com.example.order.infrastructure.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.order.application.port.out.OrderRepository;
import com.example.order.domain.Order;
import com.example.order.infrastructure.entity.OrderEntity;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

	private final OrderJpaRepository orderJpaRepository;

	public OrderRepositoryImpl(OrderJpaRepository orderJpaRepository) {
		this.orderJpaRepository = orderJpaRepository;
	}

	@Override
	public Order save(Order order) {
		return orderJpaRepository.save(OrderEntity.from(order)).toOrderDomain();
	}

	@Override
	public List<Order> findAllByMemberId(Long memberId) {
		return orderJpaRepository.findAllByMemberEntityId(memberId).stream()
			.map(OrderEntity::toOrderDomain)
			.toList();
	}
}
