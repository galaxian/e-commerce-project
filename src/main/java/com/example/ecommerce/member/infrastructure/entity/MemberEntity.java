package com.example.ecommerce.member.infrastructure.entity;

import com.example.ecommerce.common.entity.BaseEntity;
import com.example.ecommerce.member.domain.Member;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String encryptEmail;
	private String encryptName;
	private String encryptPassword;

	@Embedded
	private AddressEmbeddable addressEmbeddable;

	@Embedded
	private PhoneNumberEmbeddable phoneNumberEmbeddable;

	public MemberEntity(Long id, String encryptEmail, String encryptName, String encryptPassword, AddressEmbeddable addressEmbeddable,
		PhoneNumberEmbeddable phoneNumberEmbeddable) {
		this.id = id;
		this.encryptEmail = encryptEmail;
		this.encryptName = encryptName;
		this.encryptPassword = encryptPassword;
		this.addressEmbeddable = addressEmbeddable;
		this.phoneNumberEmbeddable = phoneNumberEmbeddable;
	}

	public static MemberEntity from(Member member) {
		return new MemberEntity(member.getId(), member.getEncryptEmail(), member.getEncryptName(),
			member.getEncryptPassword(), AddressEmbeddable.from(member.getAddress()), PhoneNumberEmbeddable.from(member.getPhoneNumber()));
	}

	public Member toMemberDomain() {
		return new Member(id, encryptEmail, encryptName, encryptPassword, addressEmbeddable.toAddressDomain(),
			phoneNumberEmbeddable.toPhoneNumberDomain(), getCreatedAt(), getUpdatedAt());
	}

}
