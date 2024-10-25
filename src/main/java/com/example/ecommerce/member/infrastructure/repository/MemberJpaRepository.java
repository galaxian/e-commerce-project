package com.example.ecommerce.member.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce.member.infrastructure.entity.MemberEntity;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {
}
