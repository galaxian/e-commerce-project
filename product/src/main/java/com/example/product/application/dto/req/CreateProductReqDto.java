package com.example.product.application.dto.req;

import java.math.BigDecimal;

public record CreateProductReqDto(
	String productName,
	String productDescription,
	BigDecimal productPrice,
	Integer stock
) {
}
