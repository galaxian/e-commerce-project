package com.example.ecommerce.member.infrastructure.entity;

import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {

	private String state;
	private String street;
	private String city;
	private String zipCode;

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
