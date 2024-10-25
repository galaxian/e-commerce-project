package com.example.ecommerce.member.domain;

import java.util.Objects;

import lombok.Getter;

@Getter
public class PhoneNumber {

	private final String countryCode;
	private final String areaCode;
	private final String number;

	public PhoneNumber(String countryCode, String areaCode, String number) {
		this.countryCode = countryCode;
		this.areaCode = areaCode;
		this.number = number;
	}

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
