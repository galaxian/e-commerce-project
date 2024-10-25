package com.example.ecommerce.member.infrastructure.repository;

import org.springframework.stereotype.Repository;

import com.example.ecommerce.member.application.port.out.MemberRepository;
import com.example.ecommerce.member.domain.Member;
import com.example.ecommerce.member.infrastructure.entity.MemberEntity;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

	private final MemberJpaRepository memberJpaRepository;

	public MemberRepositoryImpl(MemberJpaRepository memberJpaRepository) {
		this.memberJpaRepository = memberJpaRepository;
	}

	@Override
	public Member save(Member member) {
		MemberEntity memberEntity = MemberEntity.from(member);
		return memberJpaRepository.save(memberEntity).toMemberDomain();
	}
}
