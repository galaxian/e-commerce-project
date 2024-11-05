package com.example.order.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderStatusTest {

	@DisplayName("주문 상태가 PENDING인 경우 true를 반환")
	@Test
	void whenStatusIsPending_thenIsCancelableReturnsTrue() {
		// given
		OrderStatus status = OrderStatus.PENDING;

		// then
		assertThat(status.isCancelable()).isTrue();
	}

	@DisplayName("주문 상태가 AwaitingShipment인 경우 true를 반환")
	@Test
	void whenStatusIsAwaitingShipment_thenIsCancelableReturnsTrue() {
		// given
		OrderStatus status = OrderStatus.AWAITING_SHIPMENT;

		// then
		assertThat(status.isCancelable()).isTrue();
	}

	@DisplayName("주문 상태가 Shipped인 경우 false를 반환")
	@Test
	void whenStatusIsShipped_thenIsCancelableReturnsFalse() {
		// given
		OrderStatus status = OrderStatus.SHIPPED;

		// then
		assertThat(status.isCancelable()).isFalse();
	}

	@DisplayName("주문 상태가 Delivered인 경우 false를 반환")
	@Test
	void whenStatusIsDelivered_thenIsCancelableReturnsFalse() {
		// given
		OrderStatus status = OrderStatus.DELIVERED;

		// then
		assertThat(status.isCancelable()).isFalse();
	}

	@DisplayName("주문 상태가 Cancelled인 경우 false를 반환")
	@Test
	void whenStatusIsCancelled_thenIsCancelableReturnsFalse() {
		// given
		OrderStatus status = OrderStatus.CANCELLED;

		// then
		assertThat(status.isCancelable()).isFalse();
	}

}
