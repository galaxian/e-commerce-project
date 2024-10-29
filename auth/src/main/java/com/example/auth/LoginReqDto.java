package com.example.auth;

import jakarta.validation.constraints.NotNull;

public record LoginReqDto(
	@NotNull
	String email,

	@NotNull
	String password
) {
}
