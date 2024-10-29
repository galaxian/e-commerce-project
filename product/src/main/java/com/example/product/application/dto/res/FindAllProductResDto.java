package com.example.product.application.dto.res;

import java.math.BigDecimal;

import com.example.product.domain.Product;

public record FindAllProductResDto(
	String name,
	BigDecimal price,
	Integer stock
) {
	public FindAllProductResDto(Product product) {
		this(product.getName(), product.getPrice(), product.getStock());
	}
}
