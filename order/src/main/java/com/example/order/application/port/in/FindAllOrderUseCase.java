package com.example.order.application.port.in;

import java.util.List;

import com.example.order.application.dto.res.FindAllOrderResDto;

public interface FindAllOrderUseCase {
	List<FindAllOrderResDto> findAllMyOrders(Long memberId);
}
