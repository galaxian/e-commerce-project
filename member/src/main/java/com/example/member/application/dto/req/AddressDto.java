package com.example.member.application.dto.req;

public record AddressDto(
	String state,
	String street,
	String city,
	String zipCode
) {
}
