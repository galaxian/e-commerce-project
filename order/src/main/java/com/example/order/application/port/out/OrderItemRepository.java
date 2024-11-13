package com.example.order.application.port.out;

import java.util.List;

import com.example.order.domain.OrderItem;

public interface OrderItemRepository {

	OrderItem save(OrderItem orderItem);

	List<OrderItem> findByOrderId(Long orderId);
}
