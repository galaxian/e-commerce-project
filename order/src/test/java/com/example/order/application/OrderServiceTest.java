package com.example.order.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.member.application.port.out.MemberRepository;
import com.example.member.domain.Member;
import com.example.order.application.dto.req.CreateOrderReqDto;
import com.example.order.application.port.out.OrderItemRepository;
import com.example.order.application.port.out.OrderRepository;
import com.example.order.common.exception.BadRequestException;
import com.example.order.common.exception.NotFoundException;
import com.example.order.domain.Address;
import com.example.order.domain.Order;
import com.example.order.domain.OrderItem;
import com.example.order.domain.OrderStatus;
import com.example.product.application.port.out.ProductRepository;
import com.example.product.domain.Product;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

	@InjectMocks
	private OrderService orderService;

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private OrderItemRepository orderItemRepository;

	@Mock
	private ProductRepository productRepository;

	@Mock
	private MemberRepository memberRepository;

	@DisplayName("주문 등록 성공")
	@Test
	public void createOrder() {
		// given
		Member member = new Member(1L, "암호화 메일", "암호화 이름", "암호화 비밀번호",
			new com.example.member.domain.Address("서울", "ㅁㅁㅁ", "강남", "12345"), null, null, null);
		Product product = new Product(1L, "상품", "설명", BigDecimal.valueOf(10000), 10, null, null);
		CreateOrderReqDto createOrderReqDto = new CreateOrderReqDto(product.getId(), 2);

		Long userId = 1L;
		List<CreateOrderReqDto> createOrderReqDtos = Collections.singletonList(createOrderReqDto);
		BigDecimal totalAmount = product.getProductPrice().multiply(BigDecimal.valueOf(createOrderReqDto.quantity()));

		given(memberRepository.findById(userId)).willReturn(Optional.of(member));
		given(productRepository.findAllById(any())).willReturn(Collections.singletonList(product));
		given(orderRepository.save(any(Order.class))).willReturn(
			new Order(1L, totalAmount, OrderStatus.PENDING, new Address("서울", "ㅁㅁㅁ", "강남", "12345"),
				LocalDateTime.now(), null, null, member));

		// when
		Long result = orderService.createOrder(createOrderReqDtos, userId);

		// then
		then(orderRepository).should().save(any(Order.class));
		then(orderItemRepository).should().save(any(OrderItem.class));
		assertThat(result).isNotNull();
	}

	@DisplayName("주문 등록 시 사용자를 찾을 수 없는 경우 예외 발생")
	@Test
	public void createOrderWhenMemberNotFound() {
		// given
		Long userId = 1L;

		Product product = new Product(1L, "상품", "설명", BigDecimal.valueOf(10000), 10, null, null);
		CreateOrderReqDto createOrderReqDto = new CreateOrderReqDto(product.getId(), 2);

		List<CreateOrderReqDto> createOrderReqDtos = List.of(createOrderReqDto);

		given(memberRepository.findById(userId)).willReturn(Optional.empty());

		// when & then
		assertThatThrownBy(() -> orderService.createOrder(createOrderReqDtos, userId))
			.isInstanceOf(NotFoundException.class)
			.hasMessageContaining("사용자를 찾을 수 없습니다.");
	}

	@DisplayName("주문 등록 시 상품 목록을 찾을 수 없는 경우 예외 발생")
	@Test
	public void createOrderWhenProductNotFound() {
		// Given
		Long userId = 1L;

		Member member = new Member(1L, "암호화 메일", "암호화 이름", "암호화 비밀번호",
			new com.example.member.domain.Address("서울", "ㅁㅁㅁ", "강남", "12345"), null, null, null);
		Product product = new Product(1L, "상품", "설명", BigDecimal.valueOf(10000), 10, null, null);
		CreateOrderReqDto createOrderReqDto = new CreateOrderReqDto(product.getId(), 2);

		List<CreateOrderReqDto> createOrderReqDtos = List.of(createOrderReqDto);

		given(memberRepository.findById(userId)).willReturn(Optional.of(member));
		given(productRepository.findAllById(any())).willReturn(List.of());

		// When & Then
		assertThatThrownBy(() -> orderService.createOrder(createOrderReqDtos, userId))
			.isInstanceOf(NotFoundException.class)
			.hasMessageContaining("상품을 찾을 수 없습니다.");
	}

	@DisplayName("주문 등록 시 재고가 부족한 경우 예외 발생")
	@Test
	public void createOrderWhenStockIsNotEnough() {
		// given
		Member member = new Member(1L, "암호화 메일", "암호화 이름", "암호화 비밀번호",
			new com.example.member.domain.Address("서울", "ㅁㅁㅁ", "강남", "12345"), null, null, null);
		Product product = new Product(1L, "상품", "설명", BigDecimal.valueOf(10000), 1, null, null);
		CreateOrderReqDto createOrderReqDto = new CreateOrderReqDto(product.getId(), 2);

		Long userId = 1L;
		List<CreateOrderReqDto> createOrderReqDtos = Collections.singletonList(createOrderReqDto);

		given(memberRepository.findById(userId)).willReturn(Optional.of(member));
		given(productRepository.findAllById(any())).willReturn(Collections.singletonList(product));

		// when & then
		assertThatThrownBy(() -> orderService.createOrder(createOrderReqDtos, userId))
			.isInstanceOf(BadRequestException.class)
			.hasMessageContaining("재고가 부족합니다.");
	}

	@DisplayName("결제 대기 상태에서 주문 취소 성공")
	@Test
	void cancelOrder() {
		// given
		Long orderId = 1L;
		Long memberId = 1L;
		Member member = new Member(1L, "암호화 메일", "암호화 이름", "암호화 비밀번호",
			new com.example.member.domain.Address("서울", "ㅁㅁㅁ", "강남", "12345"), null, null, null);
		Order order = new Order(orderId, BigDecimal.valueOf(100), OrderStatus.PENDING, new Address("state", "Street", "City", "Zip"),
			LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), member);

		given(orderRepository.findByOrderIdAndMemberId(orderId, memberId)).willReturn(Optional.of(order));

		// when
		orderService.cancelOrder(orderId, memberId);

		// then
		assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.CANCELLED);
	}

	@DisplayName("배송 대기 상태에서 주문 취소 성공")
	@Test
	void cancelOrder2() {
		// given
		Long orderId = 1L;
		Long memberId = 1L;
		Member member = new Member(1L, "암호화 메일", "암호화 이름", "암호화 비밀번호",
			new com.example.member.domain.Address("서울", "ㅁㅁㅁ", "강남", "12345"), null, null, null);
		Order order = new Order(orderId, BigDecimal.valueOf(100), OrderStatus.AWAITING_SHIPMENT, new Address("state", "Street", "City", "Zip"),
			LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), member);

		given(orderRepository.findByOrderIdAndMemberId(orderId, memberId)).willReturn(Optional.of(order));

		// when
		orderService.cancelOrder(orderId, memberId);

		// then
		assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.CANCELLED);
	}

	@DisplayName("주문 취소할 경우 주문 상태가 배송 이후인 경우 예외 발생")
	@Test
	void cancelOrderWhenOrderStatusIsNotPendingOrAwaitingShipment() {
		// given
		Long orderId = 1L;
		Long memberId = 1L;
		Member member = new Member(1L, "암호화 메일", "암호화 이름", "암호화 비밀번호",
			new com.example.member.domain.Address("서울", "ㅁㅁㅁ", "강남", "12345"), null, null, null);
		Order order = new Order(orderId, BigDecimal.valueOf(100), OrderStatus.DELIVERED, new Address("state", "Street", "City", "Zip"),
			LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), member);

		given(orderRepository.findByOrderIdAndMemberId(orderId, memberId)).willReturn(Optional.of(order));

		// when & then
		assertThatThrownBy(() -> orderService.cancelOrder(orderId, memberId))
			.isInstanceOf(BadRequestException.class)
			.hasMessage("주문 취소는 배송시작 전까지만 가능합니다.");
	}
}
