package com.example.product.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.product.application.dto.req.CreateProductReqDto;
import com.example.product.application.port.out.ProductRepository;
import com.example.product.domain.Product;

@ExtendWith(MockitoExtension.class)
class ProductCommandServiceTest {

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private ProductCommandService productCommandService;

	@DisplayName("상품 등록")
	@Test
	void createProduct() {
		// given
		CreateProductReqDto reqDto = new CreateProductReqDto("상품명", "상품 설명", BigDecimal.valueOf(10000), 10);
		Product product = new Product(reqDto.productName(), reqDto.productDescription(), reqDto.productPrice(), reqDto.stock());
		Product savedProduct = new Product(1L, "상품명", "상품 설명", BigDecimal.valueOf(10000), 10, null, null);

		given(productRepository.save(any(Product.class))).willReturn(savedProduct);
		doNothing()
			.when(productRepository)
			.saveStock(any(Product.class));

		// when
		Long result = productCommandService.createProduct(reqDto);

		// then
		assertThat(result).isEqualTo(1L);

		then(productRepository).should().save(any(Product.class));
		then(productRepository).should().saveStock(any(Product.class));
	}

}
