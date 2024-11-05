package com.example.order.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.member.domain.Member;
import com.example.order.common.exception.BadRequestException;

import lombok.Getter;

@Getter
public class Order {

	private Long id;
	private BigDecimal totalAmount;
	private OrderStatus orderStatus;
	private Address shippingAddress;
	private LocalDateTime orderDate;
	private LocalDateTime createAt;
	private LocalDateTime updateAt;
	private Member member;

	public Order(BigDecimal totalAmount, Address shippingAddress, Member member) {
		this.totalAmount = totalAmount;
		this.orderStatus = OrderStatus.PENDING;
		this.shippingAddress = shippingAddress;
		this.orderDate = LocalDateTime.now();
		this.member = member;
	}

	public Order(Long id, BigDecimal totalAmount, OrderStatus orderStatus, Address shippingAddress,
		LocalDateTime orderDate,
		LocalDateTime createAt, LocalDateTime updateAt, Member member) {
		this.id = id;
		this.totalAmount = totalAmount;
		this.orderStatus = orderStatus;
		this.shippingAddress = shippingAddress;
		this.orderDate = orderDate;
		this.createAt = createAt;
		this.updateAt = updateAt;
		this.member = member;
	}

	public void cancel() {
		validateCancellation();
		this.orderStatus = OrderStatus.CANCELLED;
	}

	private void validateCancellation() {
		if (!orderStatus.isCancelable()) {
			throw new BadRequestException("주문 취소는 배송시작 전까지만 가능합니다.");
		}
	}
}
