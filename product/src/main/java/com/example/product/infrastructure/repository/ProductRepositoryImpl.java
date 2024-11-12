package com.example.product.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.product.application.port.out.ProductRepository;
import com.example.product.domain.Product;
import com.example.product.infrastructure.entity.ProductEntity;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

	private final ProductJpaRepository productJpaRepository;
	private final ProductRedisRepository productRedisRepository;

	public ProductRepositoryImpl(ProductJpaRepository productJpaRepository,
		ProductRedisRepository productRedisRepository) {
		this.productJpaRepository = productJpaRepository;
		this.productRedisRepository = productRedisRepository;
	}

	@Override
	public List<Product> findAll() {
		List<ProductEntity> productEntityList = productJpaRepository.findAll();
		return convertToDomain(productEntityList);
	}

	@Override
	public Optional<Product> findById(Long id) {
		return productJpaRepository.findById(id).map(ProductEntity::toDomain);
	}

	@Override
	public List<Product> findAllById(List<Long> productIds) {
		List<ProductEntity> productEntityList = productJpaRepository.findAllById(productIds);
		return convertToDomain(productEntityList);
	}

	@Override
	public Product save(Product product) {
		return productJpaRepository.save(ProductEntity.from(product)).toDomain();
	}

	@Override
	public Optional<Integer> getProductStock(Long productId) {
		return productRedisRepository.getStock(productId);
	}

	@Override
	public void saveStock(Product product) {
		productRedisRepository.saveStock(product.getId(), product.getStock());
	}

	@Override
	public void increaseStock(Long productId, Integer quantity) {
		productRedisRepository.increaseStock(productId, quantity);
	}

	@Override
	public void decreaseStock(Long productId, Integer quantity) {
		productRedisRepository.decreaseStock(productId, quantity);
	}

	private List<Product> convertToDomain(List<ProductEntity> productEntityList) {
		return productEntityList.stream()
			.map(ProductEntity::toDomain)
			.toList();
	}
}
