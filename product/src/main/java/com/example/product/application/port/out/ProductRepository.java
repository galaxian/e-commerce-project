package com.example.product.application.port.out;

import java.util.List;

import com.example.product.domain.Product;

public interface ProductRepository {

	List<Product> findAll();
}