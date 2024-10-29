package com.example.product.presentation.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.product.application.dto.res.FindAllProductResDto;
import com.example.product.application.port.in.FindAllProductUseCase;

@RestController
public class ProductController {

	private final FindAllProductUseCase findAllProductUseCase;

	public ProductController(FindAllProductUseCase findAllProductUseCase) {
		this.findAllProductUseCase = findAllProductUseCase;
	}

	@GetMapping("/products")
	public ResponseEntity<List<FindAllProductResDto>> findAllProduct() {
		return ResponseEntity.ok(findAllProductUseCase.findAllProduct());
	}
}
