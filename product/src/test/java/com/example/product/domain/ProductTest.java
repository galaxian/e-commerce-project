package com.example.product.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.example.product.common.exception.BadRequestException;

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
		assertThat(result).isFalse();
	}

	@DisplayName("재고 감소 성공")
	@ParameterizedTest
	@ValueSource(ints = {1, 2, 5, 10})
	void successDecreaseStock(int quantity) {
		// when
		product.decreaseStock(quantity);

		// then
		assertThat(product.getStock()).isEqualTo(10 - quantity);
	}

	@DisplayName("재고 감소 시 재고가 부족할 경우 예외 발생")
	@ParameterizedTest
	@ValueSource(ints = {11, 15})
	void failDecreaseStockWhenInsufficient(int quantity) {
		// then
		assertThatThrownBy(() -> product.decreaseStock(quantity))
			.isInstanceOf(BadRequestException.class)
			.hasMessage("상품 재고가 부족합니다.");
	}

	@DisplayName("재고 증가 성공")
	@ParameterizedTest
	@ValueSource(ints = {1, 5, 10})
	void successIncreaseStock(int quantity) {
		// when
		product.increaseStock(quantity);

		// then
		assertThat(product.getStock()).isEqualTo(10 + quantity);
	}
}
