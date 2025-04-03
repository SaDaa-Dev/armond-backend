package com.dev.armond.member.service;

import com.dev.armond.member.dto.SignUpDto;
import com.dev.armond.member.entity.Member;

import java.util.Optional;

public interface MemberService {
    Member signupMember(SignUpDto signUpDto);
    Optional<Member> getUser(Long id);
}
