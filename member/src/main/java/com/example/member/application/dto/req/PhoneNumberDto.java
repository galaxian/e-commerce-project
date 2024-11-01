package com.example.member.application.dto.req;

public record PhoneNumberDto(
	String countryCode,
	String areaCode,
	String number
) {
}
