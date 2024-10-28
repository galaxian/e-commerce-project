package com.example.ecommerce.member.infrastructure.entity;

import java.util.Objects;

import com.example.ecommerce.member.domain.Address;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddressEmbeddable {

	private String state;
	private String street;
	private String city;
	private String zipCode;

	public AddressEmbeddable(String state, String street, String city, String zipCode) {
		this.state = state;
		this.street = street;
		this.city = city;
		this.zipCode = zipCode;
	}

	public static AddressEmbeddable from(Address address) {
		return new AddressEmbeddable(address.getState(), address.getStreet(), address.getCity(), address.getZipCode());
	}

	public Address toAddressDomain() {
		return new Address(state, street, city, zipCode);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof AddressEmbeddable addressEmbeddable))
			return false;
		return Objects.equals(getState(), addressEmbeddable.getState()) && Objects.equals(getStreet(),
			addressEmbeddable.getStreet()) && Objects.equals(getCity(), addressEmbeddable.getCity()) && Objects.equals(
			getZipCode(), addressEmbeddable.getZipCode());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getState(), getStreet(), getCity(), getZipCode());
	}
}
