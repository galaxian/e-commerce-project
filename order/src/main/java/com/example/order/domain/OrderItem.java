package com.example.order.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.product.domain.Product;

import lombok.Getter;

@Getter
public class OrderItem {

	private Long id;
	private BigDecimal price;
	private Integer quantity;
	private Order order;
	private Product product;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public OrderItem(BigDecimal price, Integer quantity, Order order, Product product) {
		this.price = price;
		this.quantity = quantity;
		this.order = order;
		this.product = product;
	}

	public OrderItem(Long id, BigDecimal price, Integer quantity, Order order, Product product, LocalDateTime createdAt,
		LocalDateTime updatedAt) {
		this.id = id;
		this.price = price;
		this.quantity = quantity;
		this.order = order;
		this.product = product;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
}
