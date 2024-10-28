package com.example.ecommerce.common.auth;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.ecommerce.member.application.dto.req.LoginReqDto;
import com.example.ecommerce.common.auth.jwt.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;

	public LoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){

		ObjectMapper objectMapper = new ObjectMapper();

		LoginReqDto loginRequest;

		try {
			loginRequest = objectMapper.readValue(request.getInputStream(), LoginReqDto.class);
		} catch (IOException e) {
			throw new RuntimeException("Failed to parse login request", e);
		}

		String email = loginRequest.email();
		String password = loginRequest.password();

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);
		return authenticationManager.authenticate(authToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException {

		String token = generateToken(authResult);
		setResponseHeaders(response, token);
	}

	private String generateToken(Authentication authResult) {
		CustomUserDetails userDetails = (CustomUserDetails) authResult.getPrincipal();
		Long userId = userDetails.getId();
		return jwtUtil.createToken(userId);
	}

	private void setResponseHeaders(HttpServletResponse response, String token) {
		response.addHeader("Authorization", "Bearer " + token);
		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpStatus.OK.value());
	}
}
