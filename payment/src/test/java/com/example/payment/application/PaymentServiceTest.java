package com.example.payment.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.member.application.port.out.MemberRepository;
import com.example.member.domain.Member;
import com.example.order.application.port.out.OrderRepository;
import com.example.order.domain.Address;
import com.example.order.domain.Order;
import com.example.order.domain.OrderStatus;
import com.example.payment.application.port.out.PaymentRepository;
import com.example.payment.common.exception.BadRequestException;
import com.example.payment.common.exception.NotFoundException;
import com.example.payment.domain.Payment;
import com.example.payment.domain.PaymentStatus;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

	@Mock
	private PaymentRepository paymentRepository;

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private MemberRepository memberRepository;

	@InjectMocks
	private PaymentService paymentService;

	@DisplayName("결제 생성 성공")
	@Test
	void createPayment() {
		// Given
		Long orderId = 1L;
		Long memberId = 1L;

		Member member = new Member(1L, "암호화 메일", "암호화 이름", "암호화 비밀번호",
			new com.example.member.domain.Address("서울", "ㅁㅁㅁ", "강남", "12345"), null, null, null);;
		Order order = new Order(1L, BigDecimal.valueOf(15000), OrderStatus.PENDING, new Address("서울", "ㅁㅁㅁ", "강남", "12345"),
			LocalDateTime.now(), null, null, member);

		Payment payment = new Payment(1L, BigDecimal.valueOf(15000), PaymentStatus.PENDING, null, null, order, member,
			null, null);

		given(orderRepository.findByOrderIdAndMemberId(orderId, memberId))
			.willReturn(Optional.of(order));
		given(memberRepository.findById(memberId))
			.willReturn(Optional.of(member));
		given(paymentRepository.save(any(Payment.class)))
			.willReturn(payment);

		// When
		Long result = paymentService.createPayment(orderId, memberId);

		// Then
		assertThat(result).isNotNull();
		assertThat(result).isEqualTo(payment.getId());
		then(orderRepository).should(times(1)).findByOrderIdAndMemberId(orderId, memberId);
		then(memberRepository).should(times(1)).findById(memberId);
		then(paymentRepository).should(times(1)).save(any(Payment.class));
	}

	@DisplayName("결제를 생성할 때 주문을 찾을 수 없는 경우 예외 발생")
	@Test
	void createPaymentWhenOrderNotFound() {
		// Given
		Long orderId = 1L;
		Long memberId = 1L;

		given(orderRepository.findByOrderIdAndMemberId(orderId, memberId))
			.willReturn(Optional.empty());

		// When & Then
		assertThatThrownBy(() -> paymentService.createPayment(orderId, memberId))
			.isInstanceOf(NotFoundException.class)
			.hasMessageContaining("주문을 찾을 수 없습니다.");

		then(orderRepository).should(times(1)).findByOrderIdAndMemberId(orderId, memberId);
		then(memberRepository).shouldHaveNoInteractions();
	}

	@DisplayName("결제를 생성할 때 사용자를 찾을 수 없는 경우 예외 발생")
	@Test
	void createPaymentWhenMemberNotFound() {
		// Given
		Long orderId = 1L;
		Long memberId = 1L;

		Member member = new Member(1L, "암호화 메일", "암호화 이름", "암호화 비밀번호",
			new com.example.member.domain.Address("서울", "ㅁㅁㅁ", "강남", "12345"), null, null, null);;
		Order order = new Order(1L, BigDecimal.valueOf(15000), OrderStatus.PENDING, new Address("서울", "ㅁㅁㅁ", "강남", "12345"),
			LocalDateTime.now(), null, null, member);

		given(orderRepository.findByOrderIdAndMemberId(orderId, memberId))
			.willReturn(Optional.of(order));
		given(memberRepository.findById(memberId))
			.willReturn(Optional.empty());

		// When & Then
		assertThatThrownBy(() -> paymentService.createPayment(orderId, memberId))
			.isInstanceOf(NotFoundException.class)
			.hasMessageContaining("사용자를 찾을 수 없습니다.");

		then(orderRepository).should(times(1)).findByOrderIdAndMemberId(orderId, memberId);
		then(memberRepository).should(times(1)).findById(memberId);
	}

	@DisplayName("결제 진행 성공")
	@Test
	void executePayment() {
		// Given
		Long paymentId = 1L;
		Long memberId = 1L;

		Member member = new Member(1L, "암호화 메일", "암호화 이름", "암호화 비밀번호",
			new com.example.member.domain.Address("서울", "ㅁㅁㅁ", "강남", "12345"), null, null, null);;
		Order order = new Order(1L, BigDecimal.valueOf(15000), OrderStatus.PENDING, new Address("서울", "ㅁㅁㅁ", "강남", "12345"),
			LocalDateTime.now(), null, null, member);

		Payment payment = new Payment(1L, BigDecimal.valueOf(15000), PaymentStatus.PENDING, null, null, order, member,
			null, null);

		given(paymentRepository.findById(1L)).willReturn(Optional.of(payment));

		// When
		paymentService.executePaymentUseCase(paymentId, memberId);

		// Then
		assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.SUCCESS);
		assertThat(payment.getPaymentDate()).isNotNull();
		then(paymentRepository).should(times(1)).findById(1L);
	}

	@DisplayName("결제 진행 시 결제정보가 없는 경우 예외 발생")
	@Test
	void executePaymentWhenPaymentDoesNotExist() {
		// given
		Long paymentId = 1L;
		Long memberId = 1L;

		given(paymentRepository.findById(paymentId)).willReturn(Optional.empty());

		// when & then
		assertThatThrownBy(() -> paymentService.executePaymentUseCase(paymentId, memberId))
			.isInstanceOf(NotFoundException.class)
			.hasMessageContaining("결제 정보를 찾을 수 없습니다.");
	}

	@DisplayName("결제 진행 시 사용자 정보가 일치한지 않는 경우 예외 발생")
	@Test
	void executePaymentsWhenMemberDoesNotMatch() {
		// Given
		Long paymentId = 1L;
		Long memberId = 1L;

		Member member = new Member(2L, "암호화 메일", "암호화 이름", "암호화 비밀번호",
			new com.example.member.domain.Address("서울", "ㅁㅁㅁ", "강남", "12345"), null, null, null);
		;
		Order order = new Order(1L, BigDecimal.valueOf(15000), OrderStatus.PENDING,
			new Address("서울", "ㅁㅁㅁ", "강남", "12345"),
			LocalDateTime.now(), null, null, member);

		Payment payment = new Payment(1L, BigDecimal.valueOf(15000), PaymentStatus.PENDING, null, null, order, member,
			null, null);

		given(paymentRepository.findById(paymentId)).willReturn(Optional.of(payment));

		// when & then
		assertThatThrownBy(() -> paymentService.executePaymentUseCase(paymentId, memberId))
			.isInstanceOf(BadRequestException.class)
			.hasMessageContaining("해당 결제 요청 권한이 없습니다.");
	}
}
