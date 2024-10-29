package com.example.order.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.member.member.domain.Member;

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
}
