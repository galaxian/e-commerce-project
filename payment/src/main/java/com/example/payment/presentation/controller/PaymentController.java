package com.example.payment.presentation.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth.CustomUserDetails;
import com.example.payment.application.port.in.CreatePaymentUseCase;

@RestController
public class PaymentController {

	private final CreatePaymentUseCase createPaymentUseCase;

	public PaymentController(CreatePaymentUseCase createPaymentUseCase) {
		this.createPaymentUseCase = createPaymentUseCase;
	}

	@PostMapping("/orders/{orderId}/payment")
	public ResponseEntity<Long> createPayment(@PathVariable("orderId") Long orderId,
		@AuthenticationPrincipal CustomUserDetails principal) {
		Long memberId = principal.getId();
		Long paymentId = createPaymentUseCase.createPayment(memberId, orderId);
		return ResponseEntity.created(URI.create("/payment/" + paymentId)).build();
	}
}
