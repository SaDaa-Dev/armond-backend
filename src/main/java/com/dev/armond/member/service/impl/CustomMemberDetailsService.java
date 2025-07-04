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
        log.info("Attempting to load user by phone number: {}", phoneNumber);
        Member findMember = memberRepository.findMemberByPhoneNumber(phoneNumber)
                .orElseThrow(() -> {
                    log.warn("User not found with phone number: {}", phoneNumber);
                    return new UsernameNotFoundException("사용자를 찾을 수 없습니다. (전화번호: " + phoneNumber + ")");
                });

        return toDetails(findMember);
    }

    public UserDetails loadUserById(Long id) {
        log.info("Attempting to load user by member ID: {}", id);
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User not found with member ID: {}", id);
                    return new UsernameNotFoundException("사용자를 찾을 수 없습니다. (회원 ID: " + id + ")");
                });
        return toDetails(member);
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
