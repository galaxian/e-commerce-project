package com.example.payment.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.member.domain.Member;
import com.example.order.domain.Order;

import lombok.Getter;

@Getter
public class Payment {

	private Long id;
	private BigDecimal paymentAmount;
	private PaymentStatus paymentStatus;
	private PaymentMethod paymentMethod;
	private LocalDateTime paymentDate;
	private Order order;
	private Member member;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
