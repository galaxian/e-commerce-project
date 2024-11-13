package com.example.product.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.product.common.exception.BadRequestException;

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

	public Product(String productName, String productDescription, BigDecimal price, Integer stock) {
		this(null, productName, productDescription, price, stock, null, null);
	}

	public Boolean isSufficientStock(int quantity) {
		return quantity <= stock;
	}

	public void decreaseStock(Integer quantity) {
		if (!isSufficientStock(quantity)) {
			throw new BadRequestException("상품 재고가 부족합니다.");
		}
		this.stock -= quantity;
	}

	public void increaseStock(Integer quantity) {
		this.stock += quantity;
	}
}
