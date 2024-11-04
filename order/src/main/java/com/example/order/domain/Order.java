package com.example.order.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.member.domain.Member;

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
}
