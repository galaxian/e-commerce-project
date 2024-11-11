package com.example.payment.application.port.in;

public interface CreatePaymentUseCase {
	Long createPayment(Long orderId, Long memberId);
}
