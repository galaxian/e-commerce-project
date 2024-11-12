package com.example.payment.application.port.in;

public interface ExecutePaymentUseCase {
	void executePaymentUseCase(Long paymentId, Long memberId);
}
