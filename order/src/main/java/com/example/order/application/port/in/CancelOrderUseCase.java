package com.example.order.application.port.in;

public interface CancelOrderUseCase {
	void cancelOrder(Long orderId, Long memberId);
}
