package com.example.payment.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.member.application.port.out.MemberRepository;
import com.example.member.domain.Member;
import com.example.payment.application.port.in.CreatePaymentUseCase;
import com.example.order.application.port.out.OrderRepository;
import com.example.payment.application.port.out.PaymentRepository;
import com.example.order.domain.Order;
import com.example.payment.common.exception.NotFoundException;
import com.example.payment.domain.Payment;

@Transactional
@Service
public class PaymentService implements CreatePaymentUseCase {

	private final OrderRepository orderRepository;
	private final PaymentRepository paymentRepository;
	private final MemberRepository memberRepository;

	public PaymentService(OrderRepository orderRepository, PaymentRepository paymentRepository,
		MemberRepository memberRepository) {
		this.orderRepository = orderRepository;
		this.paymentRepository = paymentRepository;
		this.memberRepository = memberRepository;
	}

	@Override
	public Long createPayment(Long orderId, Long memberId) {

		Order findOrder = orderRepository.findByOrderIdAndMemberId(orderId, memberId).orElseThrow(
			() -> new NotFoundException("주문을 찾을 수 없습니다.")
		);

		Member findMember = memberRepository.findById(memberId).orElseThrow(
			() -> new NotFoundException("사용자를 찾을 수 없습니다.")
		);

		Payment payment = new Payment(findOrder, findMember);
		Payment savedPayment = paymentRepository.save(payment);

		return savedPayment.getId();
	}
}
