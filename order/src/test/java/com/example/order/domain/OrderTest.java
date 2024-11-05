package com.example.order.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderTest {

	@DisplayName("주문이 결제 대기 상태인 경우 주문 취소 가능")
	@Test
	void cancelOrder() {
		//given
		Order order = new Order(1L, BigDecimal.valueOf(100), OrderStatus.PENDING,
			new Address("state", "Street", "City", "Zip"),
			LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), null);

		//when
		order.cancel();

		//then
		assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.CANCELLED);
	}

}
