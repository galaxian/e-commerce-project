package com.example.order.domain;

import java.math.BigDecimal;

import com.example.product.domain.Product;

import lombok.Getter;

@Getter
public class OrderItem {

	private Long id;
	private BigDecimal price;
	private Integer quantity;
	private Order order;
	private Product product;
}
