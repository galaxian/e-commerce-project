package com.example.product.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class Product {

	private Long id;
	private String name;
	private String description;
	private BigDecimal price;
	private Integer stock;
	private LocalDateTime createAt;
	private LocalDateTime updateAt;

	public Product(Long id, String name, String description, BigDecimal price, Integer stock, LocalDateTime createAt,
		LocalDateTime updateAt) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.stock = stock;
		this.createAt = createAt;
		this.updateAt = updateAt;
	}
}
