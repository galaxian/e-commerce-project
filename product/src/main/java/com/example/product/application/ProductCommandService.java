package com.example.product.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.product.application.dto.req.CreateProductReqDto;
import com.example.product.application.port.in.CreateProductUseCase;
import com.example.product.application.port.out.ProductRepository;
import com.example.product.domain.Product;

@Transactional
@Service
public class ProductCommandService implements CreateProductUseCase {

	private final ProductRepository productRepository;

	public ProductCommandService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public Long createProduct(CreateProductReqDto reqDto) {
		Product product = createProductDomain(reqDto);

		// rdb 상품 정보 저장
		Product savedProduct = saveProductToRDB(product);

		// redis 재고 저장
		saveProductStockToRedis(savedProduct);

		return savedProduct.getId();
	}

	private void saveProductStockToRedis(Product savedProduct) {
		productRepository.saveStock(savedProduct);
	}

	private Product saveProductToRDB(Product product) {
		return productRepository.save(product);
	}

	private Product createProductDomain(CreateProductReqDto reqDto) {
		return new Product(reqDto.productName(), reqDto.productDescription(), reqDto.productPrice(),
			reqDto.stock());
	}
}
