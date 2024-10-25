package com.example.ecommerce.member.domain;

import lombok.Getter;

@Getter
public class Member {

	private Long id;
	private String email;
	private String name;
	private String encryptPassword;
	private Address address;
	private PhoneNumber phoneNumber;
}
