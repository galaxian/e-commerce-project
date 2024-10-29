package com.example.product.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.product.application.dto.res.FindAllProductResDto;
import com.example.product.application.port.in.FindAllProductUseCase;
import com.example.product.application.port.out.ProductRepository;
import com.example.product.domain.Product;

@Transactional
@Service
public class ProductService implements FindAllProductUseCase {

	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Transactional(readOnly = true)
	@Override
	public List<FindAllProductResDto> findAllProduct() {
		List<Product> productList = productRepository.findAll();
		return convertToDto(productList);
	}

	private static List<FindAllProductResDto> convertToDto(List<Product> productList) {
		return productList.stream()
			.map(FindAllProductResDto::new)
			.toList();
	}
}
