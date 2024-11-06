package com.example.product.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRedisRepository {

	private final RedisTemplate<String, Integer> redisTemplate;
	private final static String PRODUCT_STOCK_PREFIX = "product_stock:";

	public ProductRedisRepository(RedisTemplate<String, Integer> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public Optional<Integer> getStock(Long productId) {
		String key = PRODUCT_STOCK_PREFIX + productId;
		return Optional.ofNullable(redisTemplate.opsForValue().get(key));
	}

}
