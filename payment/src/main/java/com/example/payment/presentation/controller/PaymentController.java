package com.example.payment.presentation.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth.CustomUserDetails;
import com.example.payment.application.port.in.CreatePaymentUseCase;
import com.example.payment.application.port.in.ExecutePaymentUseCase;

@RestController
public class PaymentController {

	private final CreatePaymentUseCase createPaymentUseCase;
	private final ExecutePaymentUseCase executePaymentUseCase;

	public PaymentController(CreatePaymentUseCase createPaymentUseCase, ExecutePaymentUseCase executePaymentUseCase) {
		this.createPaymentUseCase = createPaymentUseCase;
		this.executePaymentUseCase = executePaymentUseCase;
	}

	@PostMapping("/orders/{orderId}/payment")
	public ResponseEntity<Long> createPayment(@PathVariable("orderId") Long orderId,
		@AuthenticationPrincipal CustomUserDetails principal) {
		Long memberId = principal.getId();
		Long paymentId = createPaymentUseCase.createPayment(memberId, orderId);
		return ResponseEntity.created(URI.create("/payment/" + paymentId)).build();
	}

	@PostMapping("/payment/{paymentId}/execute")
	public ResponseEntity<Void> executePayment(@PathVariable("paymentId") Long paymentId,
		@AuthenticationPrincipal CustomUserDetails principal
	) {
		executePaymentUseCase.executePaymentUseCase(paymentId, paymentId);
		return ResponseEntity.noContent().build();
	}
}
