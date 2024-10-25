package com.example.ecommerce.member.domain;

import lombok.Getter;

@Getter
public class Address {

	private String state;
	private String street;
	private String city;
	private String zipCode;

	public Address(String state, String street, String city, String zipCode) {
		this.state = state;
		this.street = street;
		this.city = city;
		this.zipCode = zipCode;
	}
}
