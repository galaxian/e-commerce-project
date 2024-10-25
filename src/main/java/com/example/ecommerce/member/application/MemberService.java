package com.example.ecommerce.member.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce.member.application.dto.req.SignUpReqDto;
import com.example.ecommerce.member.application.port.in.SignUpUseCase;
import com.example.ecommerce.member.application.port.out.MemberRepository;
import com.example.ecommerce.member.domain.Address;
import com.example.ecommerce.member.domain.Member;
import com.example.ecommerce.member.domain.PhoneNumber;

@Service
@Transactional
public class MemberService implements SignUpUseCase {

	private final MemberRepository memberRepository;

	@Autowired
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Override
	public Long signUp(SignUpReqDto reqDto) {
		Address address = new Address(reqDto.addressDto().state(), reqDto.addressDto().street(),
			reqDto.addressDto().city(),
			reqDto.addressDto().zipCode());
		PhoneNumber phoneNumber = new PhoneNumber(reqDto.phoneNumberDto().countryCode(),
			reqDto.phoneNumberDto().areaCode(), reqDto.phoneNumberDto()
			.number());

		Member member = reqDto.toMember(address, phoneNumber);
		return memberRepository.save(member).getId();
	}
}
