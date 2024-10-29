package com.example.product.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.product.application.dto.res.FindAllProductResDto;
import com.example.product.application.dto.res.FindProductResDto;
import com.example.product.application.port.out.ProductRepository;
import com.example.product.common.exception.ProductNotFoundException;
import com.example.product.domain.Product;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private ProductService productService;

	@DisplayName("상품 목록이 없는 경우 빈 리스트 반환")
	@Test
	void findAllProductsReturnEmptyList() {
	    //given
		given(productRepository.findAll())
			.willReturn(Collections.emptyList());

	    //when
		List<FindAllProductResDto> result = productService.findAllProduct();

		//then
		assertThat(result).isEmpty();
		then(productRepository).should(times(1)).findAll();

	}

	@DisplayName("상품 목록 조회")
	@Test
	public void findAllProduct() {
		// given
		Product product1 = new Product(1L, "상품1", "상품설명1", new BigDecimal(10000), 10, null, null);
		Product product2 = new Product(2L, "상품2", "상품설명2", new BigDecimal(20000), 20, null, null);
		List<Product> productList = Arrays.asList(product1, product2);

		given(productRepository.findAll()).willReturn(productList);

		// when
		List<FindAllProductResDto> result = productService.findAllProduct();

		// then
		assertThat(result)
			.hasSize(productList.size())
			.extracting(FindAllProductResDto::name)
			.containsExactly("상품1", "상품2");
		assertThat(result)
			.extracting(FindAllProductResDto::price)
			.containsExactly(new BigDecimal("10000"), new BigDecimal("20000"));

		then(productRepository).should(times(1)).findAll();
	}

	@DisplayName("상품 조회 성공")
	@Test
	public void findProduct() {
		// given
		Long productId = 1L;
		Product product = new Product(productId, "상품1", "상품설명1", BigDecimal.valueOf(10000), 10, null, null);
		given(productRepository.findById(productId))
			.willReturn(Optional.of(product));

		// when
		FindProductResDto result = productService.findProduct(productId);

		// then
		assertThat(result).isNotNull();
		assertThat(result.id()).isEqualTo(productId);
		assertThat(result.name()).isEqualTo("상품1");
		assertThat(result.description()).isEqualTo("상품설명1");
		assertThat(result.price()).isEqualTo(BigDecimal.valueOf(10000));
		assertThat(result.stock()).isEqualTo(10);
		then(productRepository).should(times(1)).findById(productId);
	}

	@DisplayName("상품이 존재하지 않는 경우 예외 발생")
	@Test
	public void findProductProductNotFound() {
		// given
		Long productId = 1L;
		given(productRepository.findById(productId))
			.willReturn(Optional.empty());

		// when
		// then
		assertThatThrownBy(
			() -> productService.findProduct(productId)
		).isInstanceOf(ProductNotFoundException.class);
	}

}
