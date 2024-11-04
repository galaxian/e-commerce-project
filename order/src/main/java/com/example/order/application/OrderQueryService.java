package com.example.order.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.order.application.dto.res.FindAllOrderResDto;
import com.example.order.application.port.in.FindAllOrderUseCase;
import com.example.order.application.port.out.OrderRepository;
import com.example.order.domain.Order;

@Transactional(readOnly = true)
@Service
public class OrderQueryService implements FindAllOrderUseCase {

	private final OrderRepository orderRepository;

	public OrderQueryService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Override
	public List<FindAllOrderResDto> findAllMyOrders(Long memberId) {

		List<Order> orderList = orderRepository.findAllByMemberId(memberId);

		return convertToDto(orderList);
	}

	private List<FindAllOrderResDto> convertToDto(List<Order> orderList) {
		return orderList.stream()
			.map(FindAllOrderResDto::new)
			.toList();
	}
}
