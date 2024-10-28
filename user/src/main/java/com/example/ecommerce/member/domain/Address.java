package com.example.ecommerce.member.domain;

import java.util.Objects;

import lombok.Getter;

@Getter
public class Address {

	private final String state;
	private final String street;
	private final String city;
	private final String zipCode;

	public Address(String state, String street, String city, String zipCode) {
		this.state = state;
		this.street = street;
		this.city = city;
		this.zipCode = zipCode;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Address address))
			return false;
		return Objects.equals(getState(), address.getState()) && Objects.equals(getStreet(),
			address.getStreet()) && Objects.equals(getCity(), address.getCity()) && Objects.equals(
			getZipCode(), address.getZipCode());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getState(), getStreet(), getCity(), getZipCode());
	}
}
