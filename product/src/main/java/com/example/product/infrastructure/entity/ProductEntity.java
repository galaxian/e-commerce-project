package com.example.product.infrastructure.entity;

import java.math.BigDecimal;

import com.example.product.common.entity.BaseEntity;
import com.example.product.domain.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product")
public class ProductEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "price", nullable = false)
	private BigDecimal price;

	@Column(name = "stock", nullable = false)
	private Integer stock;

	public ProductEntity(Long id, String name, String description, BigDecimal price, Integer stock) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.stock = stock;
	}

	public static ProductEntity from(Product product) {
		return new ProductEntity(product.getId(), product.getName(), product.getDescription(), product.getPrice(),
			product.getStock());
	}

	public Product toDomain() {
		return new Product(id, name, description, price, stock, getCreatedAt(), getUpdatedAt());
	}
}
