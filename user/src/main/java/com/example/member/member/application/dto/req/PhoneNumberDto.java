package com.example.member.member.application.dto.req;

public record PhoneNumberDto(
	String countryCode,
	String areaCode,
	String number
) {
}
