package com.dev.armond.member.repository;

import com.dev.armond.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByPhoneNumber(String email);
    boolean existsByPhoneNumber(String email);
    boolean existsByNickName(String nickName);
}
