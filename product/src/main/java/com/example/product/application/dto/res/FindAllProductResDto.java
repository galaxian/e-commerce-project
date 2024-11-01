package com.example.product.application.dto.res;

import java.math.BigDecimal;

import com.example.product.domain.Product;

public record FindAllProductResDto(
	Long id,
	String name,
	BigDecimal price,
	Integer stock
) {
	public FindAllProductResDto(Product product) {
		this(product.getId(), product.getProductName(), product.getProductPrice(), product.getStock());
	}
}
