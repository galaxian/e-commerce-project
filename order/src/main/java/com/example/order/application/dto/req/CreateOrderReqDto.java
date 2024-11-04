package com.example.order.application.dto.req;

public record CreateOrderReqDto(
	Long productId,
	Integer quantity
) {
}
