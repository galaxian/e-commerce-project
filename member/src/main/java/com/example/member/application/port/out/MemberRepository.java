package com.example.member.application.port.out;

import java.util.Optional;

import com.example.member.domain.Member;

public interface MemberRepository {
	Member save(Member member);

	Optional<Member> findByEmail(String encryptEmail);
}
