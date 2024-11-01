package com.example.ecommerce.member.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.member.application.MemberService;
import com.example.member.application.dto.req.AddressDto;
import com.example.member.application.dto.req.PhoneNumberDto;
import com.example.member.application.dto.req.SignUpReqDto;
import com.example.member.application.port.out.MemberRepository;
import com.example.member.domain.Address;
import com.example.member.domain.Member;
import com.example.member.domain.PhoneNumber;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

	@Mock
	private MemberRepository memberRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private BytesEncryptor bytesEncryptor;

	@InjectMocks
	private MemberService memberService;

	@DisplayName("회원가입 성공")
	@Test
	void signup() {
	    //given
		PhoneNumberDto phoneNumberDto = new PhoneNumberDto("82", "2", "1234567");
		AddressDto addressDto = new AddressDto("서웉특별시", "서초구", "AA동", "12345");
		SignUpReqDto reqDto = new SignUpReqDto("abcd@gmail.com", "zxcv1234!@#$", "김철수", addressDto, phoneNumberDto);

		given(memberRepository.save(any(Member.class)))
			.willReturn(
				new Member(1L, "암호화email", "암호화password", "암호화name", new Address("암호화", "암호화", "암호화", "암호화"),
					new PhoneNumber("암호화", "암호화", "암호화"),null, null));
		given(passwordEncoder.encode(anyString()))
			.willReturn("encryptPassword");
		given(bytesEncryptor.encrypt(any()))
			.willReturn("byteEncrypt".getBytes());

		//when
		Long result = memberService.signUp(reqDto);

		//then
		assertThat(result).isEqualTo(1L);
	}

}
