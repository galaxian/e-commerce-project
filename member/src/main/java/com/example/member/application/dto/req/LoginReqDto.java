package com.example.member.application.dto.req;

import jakarta.validation.constraints.NotNull;

public record LoginReqDto(
	@NotNull
	String email,

	@NotNull
	String password
) {
}
