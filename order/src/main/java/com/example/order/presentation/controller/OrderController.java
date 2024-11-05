package com.example.order.presentation.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth.CustomUserDetails;
import com.example.order.application.dto.req.CreateOrderReqDto;
import com.example.order.application.dto.res.FindAllOrderResDto;
import com.example.order.application.port.in.CancelOrderUseCase;
import com.example.order.application.port.in.CreateOrderUseCase;
import com.example.order.application.port.in.FindAllOrderUseCase;

@RestController
public class OrderController {

	private final CreateOrderUseCase createOrderUseCase;
	private final FindAllOrderUseCase findAllOrderUseCase;
	private final CancelOrderUseCase cancelOrderUseCase;

	public OrderController(CreateOrderUseCase createOrderUseCase, FindAllOrderUseCase findAllOrderUseCase,
		CancelOrderUseCase cancelOrderUseCase) {
		this.createOrderUseCase = createOrderUseCase;
		this.findAllOrderUseCase = findAllOrderUseCase;
		this.cancelOrderUseCase = cancelOrderUseCase;
	}

	@PostMapping("/orders")
	public ResponseEntity<Void> createOrder(@RequestBody List<CreateOrderReqDto> reqDtos, @AuthenticationPrincipal
		CustomUserDetails principal) {
		Long userId = principal.getId();
		Long orderId = createOrderUseCase.createOrder(reqDtos, userId);
		return ResponseEntity.created(URI.create("/orders/" + orderId)).build();
	}

	@GetMapping("/orders")
	public ResponseEntity<List<FindAllOrderResDto>> findAllOrder(@AuthenticationPrincipal CustomUserDetails principal) {
		Long memberId = principal.getId();
		return ResponseEntity.ok(findAllOrderUseCase.findAllMyOrders(memberId));
	}

	@PutMapping("/orders/{orderId}")
	public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId,
		@AuthenticationPrincipal CustomUserDetails principal) {
		cancelOrderUseCase.cancelOrder(orderId, principal.getId());
		return ResponseEntity.noContent().build();
	}
}
