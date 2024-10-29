package com.example.product.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.product.application.dto.res.FindAllProductResDto;
import com.example.product.application.dto.res.FindProductResDto;
import com.example.product.application.port.in.FindAllProductUseCase;
import com.example.product.application.port.in.FindProductUseCase;
import com.example.product.application.port.out.ProductRepository;
import com.example.product.common.exception.ProductNotFoundException;
import com.example.product.domain.Product;

@Transactional
@Service
public class ProductService implements FindAllProductUseCase, FindProductUseCase {

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

	@Transactional(readOnly = true)
	@Override
	public FindProductResDto findProduct(Long productId) {
		Product product = getProductById(productId);
		return convertToDto(product);
	}

	private Product getProductById(Long productId) {
		return productRepository.findById(productId)
			.orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));
	}

	private FindProductResDto convertToDto(Product product) {
		return new FindProductResDto(
			product.getId(),
			product.getName(),
			product.getDescription(),
			product.getPrice(),
			product.getStock()
		);
	}

	private static List<FindAllProductResDto> convertToDto(List<Product> productList) {
		return productList.stream()
			.map(FindAllProductResDto::new)
			.toList();
	}
}
