package com.example.order.application.port.out;

import java.util.List;
import java.util.Optional;

import com.example.order.domain.Order;

public interface OrderRepository {

	Order save(Order order);

	List<Order> findAllByMemberId(Long userId);

	Optional<Order> findByOrderIdAndMemberId(Long orderId, Long memberId);
}
