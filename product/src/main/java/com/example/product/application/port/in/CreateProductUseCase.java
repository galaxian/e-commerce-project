package com.example.product.application.port.in;

import com.example.product.application.dto.req.CreateProductReqDto;

public interface CreateProductUseCase {
	Long createProduct(CreateProductReqDto reqDto);
}
