package com.example.order.infrastructure.entity;

import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddressEntity {

	private String state;
	private String street;
	private String city;
	private String zipCode;

	public AddressEntity(String state, String street, String city, String zipCode) {
		this.state = state;
		this.street = street;
		this.city = city;
		this.zipCode = zipCode;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof AddressEntity that))
			return false;
		return Objects.equals(getState(), that.getState()) && Objects.equals(getStreet(),
			that.getStreet()) && Objects.equals(getCity(), that.getCity()) && Objects.equals(
			getZipCode(), that.getZipCode());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getState(), getStreet(), getCity(), getZipCode());
	}
}
