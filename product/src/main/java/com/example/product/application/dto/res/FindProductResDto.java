package com.example.product.application.dto.res;

import java.math.BigDecimal;

public record FindProductResDto(
	Long id,
	String name,
	String description,
	BigDecimal price,
	Integer stock
) {

}
