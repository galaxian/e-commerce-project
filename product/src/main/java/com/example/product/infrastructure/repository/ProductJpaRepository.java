package com.example.product.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.product.infrastructure.entity.ProductEntity;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {

}
