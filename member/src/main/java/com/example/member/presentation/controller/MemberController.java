package com.example.member.presentation.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.member.application.dto.req.SignUpReqDto;
import com.example.member.application.port.in.SignUpUseCase;

import jakarta.validation.Valid;

@RestController
public class MemberController {

	private final SignUpUseCase signUpUseCase;

	public MemberController(SignUpUseCase signUpUseCase) {
		this.signUpUseCase = signUpUseCase;
	}

	@PostMapping("/signup")
	public ResponseEntity<Void> signUp(@RequestBody @Valid SignUpReqDto reqDto) {
		Long memberId = signUpUseCase.signUp(reqDto);
		return ResponseEntity.created(URI.create("/members/" + memberId)).build();
	}
}
