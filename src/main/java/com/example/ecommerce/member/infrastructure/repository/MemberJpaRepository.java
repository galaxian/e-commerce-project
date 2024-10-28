package com.example.ecommerce.member.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce.member.domain.Member;
import com.example.ecommerce.member.infrastructure.entity.MemberEntity;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {
	Optional<MemberEntity> findByEncryptEmail(String encryptEmail);
}
