package com.example.payment.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.member.domain.Member;
import com.example.order.domain.Order;
import com.example.payment.common.exception.BadRequestException;

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

	public Payment(Order order, Member member) {
		this.paymentAmount = order.getTotalAmount();
		this.paymentStatus = PaymentStatus.PENDING;
		this.order = order;
		this.member = member;
	}

	public Payment(Long id, BigDecimal paymentAmount, PaymentStatus paymentStatus, PaymentMethod paymentMethod,
		LocalDateTime paymentDate, Order order, Member member, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.paymentAmount = paymentAmount;
		this.paymentStatus = paymentStatus;
		this.paymentMethod = paymentMethod;
		this.paymentDate = paymentDate;
		this.order = order;
		this.member = member;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public void successPayment() {
		if (!(this.paymentStatus == PaymentStatus.PENDING)) {
			throw new BadRequestException("이미 처리된 결제입니다.");
		}

		this.paymentStatus = PaymentStatus.SUCCESS;
		this.paymentMethod = PaymentMethod.CARD;
		this.paymentDate = LocalDateTime.now();
	}
}
