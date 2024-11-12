package com.example.product.application.port.out;

import java.util.List;
import java.util.Optional;

import com.example.product.domain.Product;

public interface ProductRepository {

	List<Product> findAll();
	Optional<Product> findById(Long id);
	List<Product> findAllById(List<Long> productIds);
	Product save(Product product);

	Optional<Integer> getProductStock(Long id);
	void saveStock(Product product);
	void increaseStock(Long productId, Integer quantity);
	void decreaseStock(Long productId, Integer quantity);
}
