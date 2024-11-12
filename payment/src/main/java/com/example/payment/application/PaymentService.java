package com.example.payment.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.member.application.port.out.MemberRepository;
import com.example.member.domain.Member;
import com.example.payment.application.port.in.CreatePaymentUseCase;
import com.example.order.application.port.out.OrderRepository;
import com.example.payment.application.port.in.ExecutePaymentUseCase;
import com.example.payment.application.port.out.PaymentRepository;
import com.example.order.domain.Order;
import com.example.payment.common.exception.NotFoundException;
import com.example.payment.domain.Payment;

@Transactional
@Service
public class PaymentService implements CreatePaymentUseCase, ExecutePaymentUseCase {

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

		Order findOrder = findOrderByOrderIdAndMemberId(orderId, memberId);
		Member findMember = findMemberById(memberId);

		Payment payment = new Payment(findOrder, findMember);
		Payment savedPayment = paymentRepository.save(payment);

		return savedPayment.getId();
	}

	private Order findOrderByOrderIdAndMemberId(Long orderId, Long memberId) {
		return orderRepository.findByOrderIdAndMemberId(orderId, memberId)
			.orElseThrow(() -> new NotFoundException("주문을 찾을 수 없습니다."));
	}

	private Member findMemberById(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));
	}

	@Override
	public void executePaymentUseCase(Long paymentId, Long memberId) {
		Payment findPayment = findPaymentById(paymentId);

		findPayment.validateMember(memberId);

		findPayment.successPayment();

		paymentRepository.save(findPayment);
	}

	private Payment findPaymentById(Long paymentId) {
		return paymentRepository.findById(paymentId).orElseThrow(
			() -> new NotFoundException("결제 정보를 찾을 수 없습니다.")
		);
	}
}
