package com.dev.armond.member.service.impl;

import com.dev.armond.member.dto.CustomMemberDetails;
import com.dev.armond.member.entity.Member;
import com.dev.armond.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomMemberDetailsService implements UserDetailsService {
    private final MemberRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        Member member = userRepository.findMemberByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return new CustomMemberDetails(member.getId(), member.getPhoneNumber(), member.getPassword(), member.getAuthorities());
    }
}
