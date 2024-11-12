package com.example.product.presentation.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.product.application.dto.req.CreateProductReqDto;
import com.example.product.application.dto.res.FindAllProductResDto;
import com.example.product.application.dto.res.FindProductResDto;
import com.example.product.application.port.in.CreateProductUseCase;
import com.example.product.application.port.in.FindAllProductUseCase;
import com.example.product.application.port.in.FindProductUseCase;

@RestController
public class ProductController {

	private final FindAllProductUseCase findAllProductUseCase;
	private final FindProductUseCase findProductUseCase;
	private final CreateProductUseCase createProductUseCase;

	public ProductController(FindAllProductUseCase findAllProductUseCase, FindProductUseCase findProductUseCase,
		CreateProductUseCase createProductUseCase) {
		this.findAllProductUseCase = findAllProductUseCase;
		this.findProductUseCase = findProductUseCase;
		this.createProductUseCase = createProductUseCase;
	}

	@GetMapping("/products")
	public ResponseEntity<List<FindAllProductResDto>> findAllProduct() {
		return ResponseEntity.ok(findAllProductUseCase.findAllProduct());
	}

	@GetMapping("/products/{id}")
	public ResponseEntity<FindProductResDto> findProduct(@PathVariable Long id) {
		return ResponseEntity.ok(findProductUseCase.findProduct(id));
	}

	@PostMapping("/products")
	public ResponseEntity<Void> createProduct(@RequestBody CreateProductReqDto reqDto) {
		Long productId = createProductUseCase.createProduct(reqDto);
		return ResponseEntity.created(URI.create("/products/" + productId)).build();
	}
}
