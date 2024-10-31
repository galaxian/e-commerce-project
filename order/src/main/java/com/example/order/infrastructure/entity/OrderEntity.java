package com.example.order.infrastructure.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.member.member.infrastructure.entity.MemberEntity;
import com.example.order.common.entity.BaseEntity;
import com.example.order.domain.Address;
import com.example.order.domain.Order;
import com.example.order.domain.OrderStatus;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private BigDecimal totalAmount;

	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

	@Embedded
	private AddressEntity shippingAddress;

	private LocalDateTime orderDate;

	@ManyToOne(fetch = FetchType.LAZY)
	private MemberEntity memberEntity;

	public OrderEntity(Long id, BigDecimal totalAmount, OrderStatus orderStatus, AddressEntity shippingAddress,
		LocalDateTime orderDate, MemberEntity memberEntity) {
		this.id = id;
		this.totalAmount = totalAmount;
		this.orderStatus = orderStatus;
		this.shippingAddress = shippingAddress;
		this.orderDate = orderDate;
		this.memberEntity = memberEntity;
	}

	public static OrderEntity from(Order order) {
		return new OrderEntity(order.getId(), order.getTotalAmount(), order.getOrderStatus(),
			new AddressEntity(order.getShippingAddress().getState(), order.getShippingAddress().getStreet(),
				order.getShippingAddress().getCity(), order.getShippingAddress().getZipCode()),
			order.getOrderDate(), MemberEntity.from(order.getMember()));
	}

	public Order toOrderDomain() {
		return new Order(id, totalAmount, orderStatus, new Address(shippingAddress.getState(), shippingAddress.getStreet(), shippingAddress.getStreet(),
			shippingAddress.getZipCode()), orderDate, getCreatedAt(), getUpdatedAt(), memberEntity.toMemberDomain());
	}
}
