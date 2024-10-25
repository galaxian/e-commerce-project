package com.example.ecommerce.member.application.dto.req;

public record PhoneNumberVo(
	String countryCode,
	String areaCode,
	String number
) {
}
