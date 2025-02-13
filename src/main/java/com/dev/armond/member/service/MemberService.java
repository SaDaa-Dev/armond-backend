package com.dev.armond.member.service;

import com.dev.armond.member.dto.SignUpRequest;
import com.dev.armond.member.entity.Member;

import java.util.Optional;

public interface MemberService {
    Member registerUser(SignUpRequest signUpRequest);
    Optional<Member> getUser(Long id);
}
