package com.dev.armond.member.service;

import com.dev.armond.member.dto.RegisterRequestDto;
import com.dev.armond.member.entity.Member;

import java.util.Optional;

public interface MemberService {
    Member registerMember(RegisterRequestDto registerRequestDto);
    Optional<Member> getUser(Long id);
}
