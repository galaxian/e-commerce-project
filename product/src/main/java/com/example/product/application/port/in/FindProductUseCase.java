package com.example.product.application.port.in;

import com.example.product.application.dto.res.FindProductResDto;

public interface FindProductUseCase {

	FindProductResDto findProduct(Long productId);
}
