package com.example.ecommerce.common.auth.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private final Long expiredTime;
	private final SecretKey secretKey;

	public JwtUtil(@Value("${spring.jwt.secret}") String secret, @Value("${spring.jwt.expired}") Long expiredTime) {
		this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
		this.expiredTime = expiredTime;
	}

	public String createToken(Long userPk) {
		return Jwts.builder()
			.claim("id", userPk)
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + expiredTime))
			.signWith(secretKey)
			.compact();
	}

	public Long getId(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(token)
			.getBody()
			.get("id", Long.class);
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException e) {
			throw new ExpiredJwtException(e.getHeader(), e.getClaims(), "JWT token is expired");
		} catch (Exception e) {
			throw new JwtException("Invalid JWT token");
		}
	}
}
