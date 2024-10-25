package com.example.ecommerce.member.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce.member.application.dto.req.SignUpReqDto;
import com.example.ecommerce.member.application.port.in.SignUpUseCase;
import com.example.ecommerce.member.application.port.out.memberRepository;
import com.example.ecommerce.member.domain.Member;

@Service
@Transactional
public class MemberService implements SignUpUseCase {

	private final memberRepository memberRepository;

	@Autowired
	public MemberService(memberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Override
	public Long signUp(SignUpReqDto reqDto) {
		Member member = reqDto.toMember();
		return memberRepository.save(member).getId();
	}
}
