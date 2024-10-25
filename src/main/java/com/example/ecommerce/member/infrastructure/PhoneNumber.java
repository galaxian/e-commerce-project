package com.example.ecommerce.member.infrastructure;

import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class PhoneNumber {

	private String countryCode;
	private String areaCode;
	private String number;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof PhoneNumber that))
			return false;
		return Objects.equals(getCountryCode(), that.getCountryCode()) && Objects.equals(getAreaCode(),
			that.getAreaCode()) && Objects.equals(getNumber(), that.getNumber());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getCountryCode(), getAreaCode(), getNumber());
	}
}
