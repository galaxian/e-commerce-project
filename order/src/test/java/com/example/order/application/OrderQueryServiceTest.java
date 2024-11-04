package com.example.order.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.member.domain.Member;
import com.example.order.application.dto.res.FindAllOrderResDto;
import com.example.order.application.port.out.OrderRepository;
import com.example.order.domain.Address;
import com.example.order.domain.Order;
import com.example.order.domain.OrderStatus;

@ExtendWith(MockitoExtension.class)
class OrderQueryServiceTest {

	@Mock
	private OrderRepository orderRepository;

	@InjectMocks
	private OrderQueryService orderQueryService;

	@DisplayName("주문 목록 조회")
	@Test
	void findAllMyOrders() {
		// Given
		Long memberId = 1L;
		Member member = new Member(1L, null, null, null, null, null, null, null);

		Address orderAddress = new Address("서울", "강남", "동", "12345");
		Order order1 = new Order(1L, BigDecimal.valueOf(10000.00), OrderStatus.PENDING, orderAddress, LocalDateTime.now(),
			null, null, member);
		Order order2 = new Order(2L, BigDecimal.valueOf(13333.00), OrderStatus.PENDING, orderAddress, LocalDateTime.now(),
			null, null, member);

		List<Order> orders = List.of(order1, order2);
		given(orderRepository.findAllByMemberId(memberId)).willReturn(orders);

		// When
		List<FindAllOrderResDto> result = orderQueryService.findAllMyOrders(memberId);

		// Then
		assertThat(result).isNotNull();
		assertThat(result).hasSize(2);

		assertThat(result.get(0)).isEqualTo(new FindAllOrderResDto(order1));
		assertThat(result.get(1)).isEqualTo(new FindAllOrderResDto(order2));
	}

	@DisplayName("주문 목록이 없는 경우 빈 리스트 반환")
	@Test
	void findAllMyOrdersWhenNoOrdersExist() {
		// Given
		Long memberId = 1L;
		given(orderRepository.findAllByMemberId(memberId)).willReturn(Collections.emptyList());

		// When
		List<FindAllOrderResDto> result = orderQueryService.findAllMyOrders(memberId);

		// Then
		assertThat(result).isNotNull();
		assertThat(result).isEmpty();
	}

}
