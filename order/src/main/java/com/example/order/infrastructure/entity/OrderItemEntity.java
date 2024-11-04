package com.example.order.infrastructure.entity;

import java.math.BigDecimal;

import com.example.order.common.entity.BaseEntity;
import com.example.order.domain.OrderItem;
import com.example.product.infrastructure.entity.ProductEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class OrderItemEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private BigDecimal price;

	@Column(nullable = false)
	private Integer quantity;

	@ManyToOne(fetch = FetchType.LAZY)
	private OrderEntity orderEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	private ProductEntity productEntity;

	public OrderItemEntity(Long id, BigDecimal price, Integer quantity, OrderEntity orderEntity,
		ProductEntity productEntity) {
		this.id = id;
		this.price = price;
		this.quantity = quantity;
		this.orderEntity = orderEntity;
		this.productEntity = productEntity;
	}

	public static OrderItemEntity from(OrderItem orderItem) {
		return new OrderItemEntity(orderItem.getId(), orderItem.getPrice(), orderItem.getQuantity(),
			OrderEntity.from(orderItem.getOrder()), ProductEntity.from(orderItem.getProduct()));
	}

	public OrderItem toDomain() {
		return new OrderItem(id, price, quantity, orderEntity.toOrderDomain(), productEntity.toDomain(), getCreatedAt(),
			getUpdatedAt());
	}
}
