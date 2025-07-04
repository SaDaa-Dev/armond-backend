package com.dev.armond.member.repository;

import com.dev.armond.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // PhoneNumber
    Optional<Member> findMemberByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumber(String phoneNumber);

    // Email
    Optional<Member> findMemberByEmail(String email);
    boolean existsByEmail(String email);

    boolean existsByName(String memberName);
    boolean existsByNickName(String nickName);
}
