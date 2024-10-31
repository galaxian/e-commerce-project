package com.example.order.application.port.in;

import java.util.List;

import com.example.order.application.dto.req.CreateOrderReqDto;

public interface CreateOrderUseCase {

	Long createOrder(List<CreateOrderReqDto> createOrderReqDtos, Long userId);
}
