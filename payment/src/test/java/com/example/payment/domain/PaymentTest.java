package com.example.payment.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.member.domain.Member;
import com.example.order.domain.Address;
import com.example.order.domain.Order;
import com.example.order.domain.OrderStatus;
import com.example.payment.common.exception.BadRequestException;

class PaymentTest {

	@DisplayName("결제 요청자 검증 성공")
	@Test
	void validateMember() {
	    //given
		Long memberId = 1L;

		Member member = new Member(1L, "암호화 메일", "암호화 이름", "암호화 비밀번호",
			new com.example.member.domain.Address("서울", "ㅁㅁㅁ", "강남", "12345"), null, null, null);;
		Order order = new Order(1L, BigDecimal.valueOf(15000), OrderStatus.PENDING, new Address("서울", "ㅁㅁㅁ", "강남", "12345"),
			LocalDateTime.now(), null, null, member);

		Payment payment = new Payment(1L, BigDecimal.valueOf(15000), PaymentStatus.PENDING, null, null, order, member,
			null, null);

	    //when & then
		assertThatCode(() -> payment.validateMember(memberId))
			.doesNotThrowAnyException();

	}

	@DisplayName("결제 요청자 검증 실패시 예외 발생")
	@Test
	void validateMemberWhenMemberDoesNotMatch() {
		//given
		Long memberId = 1L;

		Member member = new Member(2L, "암호화 메일", "암호화 이름", "암호화 비밀번호",
			new com.example.member.domain.Address("서울", "ㅁㅁㅁ", "강남", "12345"), null, null, null);;
		Order order = new Order(1L, BigDecimal.valueOf(15000), OrderStatus.PENDING, new Address("서울", "ㅁㅁㅁ", "강남", "12345"),
			LocalDateTime.now(), null, null, member);

		Payment payment = new Payment(1L, BigDecimal.valueOf(15000), PaymentStatus.PENDING, null, null, order, member,
			null, null);

		//when & then
		assertThatThrownBy(() -> payment.validateMember(memberId))
			.isInstanceOf(BadRequestException.class)
			.hasMessageContaining("해당 결제 요청 권한이 없습니다.");

	}

	@DisplayName("결제 처리 성공")
	@Test
	void successPayment() {
		//given
		Member member = new Member(1L, "암호화 메일", "암호화 이름", "암호화 비밀번호",
			new com.example.member.domain.Address("서울", "ㅁㅁㅁ", "강남", "12345"), null, null, null);;
		Order order = new Order(1L, BigDecimal.valueOf(15000), OrderStatus.PENDING, new Address("서울", "ㅁㅁㅁ", "강남", "12345"),
			LocalDateTime.now(), null, null, member);

		Payment payment = new Payment(1L, BigDecimal.valueOf(15000), PaymentStatus.PENDING, null, null, order, member,
			null, null);

		//when
		payment.successPayment();

		//then
		assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.SUCCESS);
		assertThat(payment.getPaymentMethod()).isEqualTo(PaymentMethod.CARD);
		assertThat(payment.getPaymentDate()).isNotNull();

	}

	@DisplayName("이미 처리된 결제인 경우 예외 발생")
	@Test
	void successPaymentWhenPaymentIsNotPending() {
		//given
		Member member = new Member(1L, "암호화 메일", "암호화 이름", "암호화 비밀번호",
			new com.example.member.domain.Address("서울", "ㅁㅁㅁ", "강남", "12345"), null, null, null);;
		Order order = new Order(1L, BigDecimal.valueOf(15000), OrderStatus.PENDING, new Address("서울", "ㅁㅁㅁ", "강남", "12345"),
			LocalDateTime.now(), null, null, member);

		Payment payment = new Payment(1L, BigDecimal.valueOf(15000), PaymentStatus.SUCCESS, null, null, order, member,
			null, null);

		// when & then
		assertThatThrownBy(payment::successPayment)
			.isInstanceOf(BadRequestException.class)
			.hasMessageContaining("이미 처리된 결제입니다.");

	}
}
