package com.example.ecommerce.member.application.dto.req;

import com.example.ecommerce.member.domain.Address;
import com.example.ecommerce.member.domain.Member;
import com.example.ecommerce.member.domain.PhoneNumber;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public record SignUpReqDto(
	@NotNull
	String email,

	@NotNull
	String password,

	@NotNull
	String name,

	@NotBlank
	Address address,

	@NotBlank
	PhoneNumber phoneNumber
) {
	public Member toMember() {
		return new Member(email, password, name, address, phoneNumber);
	}
}
