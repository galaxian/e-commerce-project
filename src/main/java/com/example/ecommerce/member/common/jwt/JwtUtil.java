package com.example.ecommerce.member.common.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${spring.jwt.expired}")
	private long expiredTime;

	private final SecretKey secretKey;

	public JwtUtil(@Value("${spring.jwt.secret}") String secret) {
		this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}

	public String createToken(Long userPk) {
		return Jwts.builder()
			.claim("id", userPk)
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + expiredTime))
			.signWith(secretKey)
			.compact();
	}
}
