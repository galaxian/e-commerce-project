package com.example.product.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class Product {

	private Long id;
	private String name;
	private String description;
	private BigDecimal price;
	private Integer stock;
	private LocalDateTime createAt;
	private LocalDateTime updateAt;
}
