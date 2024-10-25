package com.example.ecommerce.member.infrastructure.entity;

import java.util.Objects;

import com.example.ecommerce.member.domain.PhoneNumber;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PhoneNumberVo {

	private String countryCode;
	private String areaCode;
	private String number;

	public PhoneNumberVo(String countryCode, String areaCode, String number) {
		this.countryCode = countryCode;
		this.areaCode = areaCode;
		this.number = number;
	}

	public static PhoneNumberVo from(PhoneNumber phoneNumber) {
		return new PhoneNumberVo(phoneNumber.getCountryCode(), phoneNumber.getAreaCode(), phoneNumber.getNumber());
	}

	public PhoneNumber toPhoneNumberDomain() {
		return new PhoneNumber(countryCode, areaCode, number);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof PhoneNumberVo that))
			return false;
		return Objects.equals(getCountryCode(), that.getCountryCode()) && Objects.equals(getAreaCode(),
			that.getAreaCode()) && Objects.equals(getNumber(), that.getNumber());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getCountryCode(), getAreaCode(), getNumber());
	}
}
