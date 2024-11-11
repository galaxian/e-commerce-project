package com.example.payment.application.port.out;

import com.example.payment.domain.Payment;

public interface PaymentRepository {
	Payment save(Payment payment);
}
