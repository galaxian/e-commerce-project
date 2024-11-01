package com.example.member.domain;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class Member {

	private Long id;
	private String encryptEmail;
	private String encryptName;
	private String encryptPassword;
	private Address address;
	private PhoneNumber phoneNumber;
	private LocalDateTime createAt;
	private LocalDateTime updateAt;

	public Member(String encryptEmail, String encryptName, String encryptPassword, Address address, PhoneNumber phoneNumber) {
		this.encryptEmail = encryptEmail;
		this.encryptName = encryptName;
		this.encryptPassword = encryptPassword;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}

	public Member(Long id, String encryptEmail, String encryptName, String encryptPassword, Address address,
		PhoneNumber phoneNumber, LocalDateTime createAt, LocalDateTime updateAt) {
		this.id = id;
		this.encryptEmail = encryptEmail;
		this.encryptName = encryptName;
		this.encryptPassword = encryptPassword;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.createAt = createAt;
		this.updateAt = updateAt;
	}
}
