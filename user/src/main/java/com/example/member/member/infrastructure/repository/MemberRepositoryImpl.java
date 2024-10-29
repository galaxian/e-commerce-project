package com.example.member.member.infrastructure.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.member.member.application.port.out.MemberRepository;
import com.example.member.member.domain.Member;
import com.example.member.member.infrastructure.entity.MemberEntity;

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

	@Override
	public Optional<Member> findByEmail(String encryptEmail) {
		return memberJpaRepository.findByEncryptEmail(encryptEmail).map(MemberEntity::toMemberDomain);
	}

}
