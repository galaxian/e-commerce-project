package com.example.product.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ProductTest {

	private Product product;

	@BeforeEach
	public void setUp() {
		product = new Product(1L, "상품 이름 테스트", "상품 설명 테스트", new BigDecimal(10000), 10, null, null);
	}

	@DisplayName("구매 가능 여부 검증 통과")
	@ParameterizedTest
	@ValueSource(ints = {1, 2, 7, 10})
	void successIsSufficientStock(int quantity) {
	    //when
		Boolean result = product.isSufficientStock(quantity);

		//then
		assertThat(result).isTrue();
	}

	@DisplayName("재고 수 이상 구매 시 구매 가능 여부 검증 실패")
	@ParameterizedTest
	@ValueSource(ints = {11, 20, 100})
	void failIsSufficientStockOver(int quantity) {
		//when
		Boolean result = product.isSufficientStock(quantity);

		//then
		assertThat(result).isTrue();
	}
}
