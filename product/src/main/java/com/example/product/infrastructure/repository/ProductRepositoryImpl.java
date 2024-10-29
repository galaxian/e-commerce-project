package com.example.product.infrastructure.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.product.application.port.out.ProductRepository;
import com.example.product.domain.Product;
import com.example.product.infrastructure.entity.ProductEntity;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

	private final ProductJpaRepository productJpaRepository;

	public ProductRepositoryImpl(ProductJpaRepository productJpaRepository) {
		this.productJpaRepository = productJpaRepository;
	}

	@Override
	public List<Product> findAll() {
		List<ProductEntity> productEntityList = productJpaRepository.findAll();
		return convertToDomain(productEntityList);
	}

	private List<Product> convertToDomain(List<ProductEntity> productEntityList) {
		return productEntityList.stream()
			.map(ProductEntity::toDomain)
			.toList();
	}
}
