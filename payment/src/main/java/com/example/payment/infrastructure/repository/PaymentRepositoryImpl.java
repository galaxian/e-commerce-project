package com.example.payment.infrastructure.repository;

import org.springframework.stereotype.Repository;

import com.example.payment.application.port.out.PaymentRepository;
import com.example.payment.domain.Payment;
import com.example.payment.infrastructure.entity.PaymentEntity;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

	private final PaymentJpaRepository paymentJpaRepository;

	public PaymentRepositoryImpl(PaymentJpaRepository paymentJpaRepository) {
		this.paymentJpaRepository = paymentJpaRepository;
	}

	@Override
	public Payment save(Payment payment) {
		return paymentJpaRepository.save(PaymentEntity.from(payment)).toPaymentDomain();
	}
}