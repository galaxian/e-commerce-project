package com.example.ecommerce.member.application.port.out;

import com.example.ecommerce.member.domain.Member;

public interface MemberRepository {
	Member save(Member member);
}
