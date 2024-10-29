package com.example.member.member.application.dto.req;

import com.example.member.member.domain.Address;
import com.example.member.member.domain.Member;
import com.example.member.member.domain.PhoneNumber;

import jakarta.validation.constraints.NotNull;

public record SignUpReqDto(
	@NotNull
	String email,

	@NotNull
	String password,

	@NotNull
	String name,

	@NotNull
	AddressDto addressDto,

	@NotNull
	PhoneNumberDto phoneNumberDto
) {
	public Member toMember(Address address, PhoneNumber phoneNumber) {
		return new Member(email, password, name, address, phoneNumber);
	}
}
