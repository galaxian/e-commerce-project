package com.example.order.application.dto.res;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.order.domain.Order;

public record FindAllOrderResDto(
	Long orderId,
	BigDecimal totalAmount,
	LocalDateTime orderDate
	) {

	public FindAllOrderResDto(Order order) {
		this(order.getId(), order.getTotalAmount(), order.getOrderDate());
	}
}
