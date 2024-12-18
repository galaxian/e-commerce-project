package com.example.member.application;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.auth.CustomUserDetails;
import com.example.member.application.dto.req.AddressDto;
import com.example.member.application.dto.req.PhoneNumberDto;
import com.example.member.application.dto.req.SignUpReqDto;
import com.example.member.application.port.in.SignUpUseCase;
import com.example.member.application.port.out.MemberRepository;
import com.example.member.common.exception.UnauthorizedException;
import com.example.member.domain.Address;
import com.example.member.domain.Member;
import com.example.member.domain.PhoneNumber;

@Service
@Transactional
public class MemberService implements SignUpUseCase, UserDetailsService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final BytesEncryptor bytesEncryptor;

	@Autowired
	public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder,
		BytesEncryptor bytesEncryptor) {
		this.memberRepository = memberRepository;
		this.passwordEncoder = passwordEncoder;
		this.bytesEncryptor = bytesEncryptor;
	}

	@Override
	public Long signUp(SignUpReqDto reqDto) {
		Member member = createEncryptMember(reqDto);
		return memberRepository.save(member).getId();
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		String encryptEmail = encrypt(email);
		Member member = memberRepository.findByEmail(encryptEmail).orElseThrow(
			() -> new UnauthorizedException("사용자를 찾을 수 없습니다.")
		);
		return new CustomUserDetails(encryptEmail, member.getId(), member.getEncryptPassword(), null);
	}

	private Member createEncryptMember(SignUpReqDto reqDto) {
		String encryptPassword = passwordEncoder.encode(reqDto.password());
		Address address = createEncryptAddress(reqDto.addressDto());
		PhoneNumber phoneNumber = createEncryptPhoneNumber(reqDto.phoneNumberDto());

		String encryptEmail = encrypt(reqDto.email());
		String encryptName = encrypt(reqDto.name());

		return new Member(encryptEmail, encryptName, encryptPassword, address, phoneNumber);
	}

	private Address createEncryptAddress(AddressDto addressDto) {
		String encryptState = encrypt(addressDto.state());
		String encryptCity = encrypt(addressDto.city());
		String encryptStreet = encrypt(addressDto.street());
		String encryptZipCode = encrypt(addressDto.zipCode());

		return new Address(encryptState, encryptStreet, encryptCity, encryptZipCode);
	}

	private PhoneNumber createEncryptPhoneNumber(PhoneNumberDto phoneNumberDto) {
		String encryptCountryCode = encrypt(phoneNumberDto.countryCode());
		String encryptAreaCode = encrypt(phoneNumberDto.areaCode());
		String encryptNumber = encrypt(phoneNumberDto.number());

		return new PhoneNumber(encryptCountryCode, encryptAreaCode, encryptNumber);
	}

	private String encrypt(String raw) {
		byte[] encryptedBytes = bytesEncryptor.encrypt(raw.getBytes(StandardCharsets.UTF_8));
		return Base64.getEncoder().encodeToString(encryptedBytes);
	}
}
