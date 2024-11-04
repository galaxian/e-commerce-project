package com.example.order.application.port.out;

import com.example.order.domain.Order;

public interface OrderRepository {

	Order save(Order order);
}
