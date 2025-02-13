package com.dev.armond.member.service.impl;

import com.dev.armond.member.dto.CustomMemberDetails;
import com.dev.armond.member.entity.Member;
import com.dev.armond.member.repository.MemberRepository;
import com.dev.armond.member.service.MemberDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements MemberDetailService {
    private final MemberRepository userRepository;
    @Override
    public CustomMemberDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return new CustomMemberDetails(user.getEmail(), user.getPassword(), true, user.getAuthorities());
    }
}
