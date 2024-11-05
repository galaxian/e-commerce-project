package com.example.order.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.order.infrastructure.entity.OrderEntity;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {
	List<OrderEntity> findAllByMemberEntityId(Long memberId);

	Optional<OrderEntity> findByIdAndMemberEntityId(Long orderId, Long memberId);
}
