package com.example.product.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class Product {

	private Long id;
	private String productName;
	private String productDescription;
	private BigDecimal productPrice;
	private Integer stock;
	private LocalDateTime createAt;
	private LocalDateTime updateAt;

	public Product(Long id, String productName, String productDescription, BigDecimal price, Integer stock, LocalDateTime createAt,
		LocalDateTime updateAt) {
		this.id = id;
		this.productName = productName;
		this.productDescription = productDescription;
		this.productPrice = price;
		this.stock = stock;
		this.createAt = createAt;
		this.updateAt = updateAt;
	}

	public Boolean isSufficientStock(int quantity) {
		return quantity <= stock;
	}
}
