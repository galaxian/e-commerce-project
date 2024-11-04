package com.example.order.application.port.out;

import java.util.List;

import com.example.order.domain.Order;

public interface OrderRepository {

	Order save(Order order);

	List<Order> findAllByMemberId(Long userId);
}
