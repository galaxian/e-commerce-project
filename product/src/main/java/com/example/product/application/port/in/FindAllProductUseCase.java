package com.example.product.application.port.in;

import java.util.List;

import com.example.product.application.dto.res.FindAllProductResDto;

public interface FindAllProductUseCase {

	List<FindAllProductResDto> findAllProduct();
}
