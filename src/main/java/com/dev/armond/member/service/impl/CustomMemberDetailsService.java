package com.dev.armond.member.service.impl;

import com.dev.armond.member.dto.CustomMemberDetails;
import com.dev.armond.member.entity.Member;
import com.dev.armond.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomMemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        Member member = memberRepository.findMemberByPhoneNumber(phoneNumber)
                    .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다. (By phoneNumber)"));

        return toDetails(member);
    }

    public UserDetails loadUserById(Long id) {
        Member m = memberRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다. (By MemberId)"));
        return toDetails(m);
    }

    private CustomMemberDetails toDetails(Member m) {
        return new CustomMemberDetails(
                m.getId(),
                m.getPhoneNumber(),
                m.getPassword(),
                m.getAuthorities()
        );
    }

}
