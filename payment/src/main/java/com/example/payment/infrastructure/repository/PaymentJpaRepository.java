package com.example.payment.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.payment.infrastructure.entity.PaymentEntity;

public interface PaymentJpaRepository extends JpaRepository<PaymentEntity, Long> {
}
