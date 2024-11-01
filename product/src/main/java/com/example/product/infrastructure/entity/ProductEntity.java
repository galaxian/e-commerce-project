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

	@Column(name = "product_name", nullable = false)
	private String productName;

	@Column(name = "product_description", nullable = false)
	private String productDescription;

	@Column(name = "price", nullable = false)
	private BigDecimal productPrice;

	@Column(name = "stock", nullable = false)
	private Integer stock;

	public ProductEntity(Long id, String productName, String productDescription, BigDecimal productPrice, Integer stock) {
		this.id = id;
		this.productName = productName;
		this.productDescription = productDescription;
		this.productPrice = productPrice;
		this.stock = stock;
	}

	public static ProductEntity from(Product product) {
		return new ProductEntity(product.getId(), product.getProductName(), product.getProductDescription(), product.getProductPrice(),
			product.getStock());
	}

	public Product toDomain() {
		return new Product(id, productName, productDescription, productPrice, stock, getCreatedAt(), getUpdatedAt());
	}
}
