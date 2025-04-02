package com.dev.armond.member.service.impl;

import com.dev.armond.member.dto.SignUpDto;
import com.dev.armond.member.entity.Role;
import com.dev.armond.member.entity.Member;
import com.dev.armond.member.repository.RoleRepository;
import com.dev.armond.member.repository.MemberRepository;
import com.dev.armond.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member registerUser(SignUpDto request) {
        if(memberRepository.existsByPhoneNumber(request.phoneNumber())){
            throw new RuntimeException("이미 등록된 이메일입니다.");
        }

        if (memberRepository.existsByNickName(request.nickName())) {
            throw new RuntimeException("이미 존재하는 닉네임입니다.");
        }

        Role defaultRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("기본 권한이 없습니다."));

        String encodedPassword = passwordEncoder.encode(request.password());

        Member user = Member.builder()
                .name(request.name())
                .nickName(request.nickName())
                .password(encodedPassword)
                .phoneNumber(request.phoneNumber())
                .gender(request.gender())
                .height(request.height())
                .weight(request.weight())
                .goalCalories(request.goalCalories())
                .roles(Set.of(defaultRole))
                .build();

        return memberRepository.save(user);
    }

    @Override
    public Optional<Member> getUser(Long id) {
        return memberRepository.findById(id);
    }
}
