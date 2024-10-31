package com.example.order.application.port.out;

import com.example.order.domain.OrderItem;

public interface OrderItemRepository {

	OrderItem save(OrderItem orderItem);
}
