package com.example.payment.application.port.out;

import java.util.Optional;

import com.example.payment.domain.Payment;

public interface PaymentRepository {
	Payment save(Payment payment);

	Optional<Payment> findById(Long paymentId);
}
