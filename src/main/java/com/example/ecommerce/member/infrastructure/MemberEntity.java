package com.example.ecommerce.member.infrastructure;

import com.example.ecommerce.common.entity.BaseEntity;
import com.example.ecommerce.member.domain.Address;
import com.example.ecommerce.member.domain.PhoneNumber;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class MemberEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String encryptEmail;
	private String encryptName;
	private String encryptPassword;

	@Embedded
	private Address address;

	@Embedded
	private PhoneNumber phoneNumber;
}
