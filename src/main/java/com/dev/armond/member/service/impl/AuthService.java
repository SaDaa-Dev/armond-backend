package com.dev.armond.member.service.impl;

import com.dev.armond.common.util.JwtUtil;
import com.dev.armond.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    public String login(String phoneNumber, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(phoneNumber, password)
            );

            memberRepository.findMemberByPhoneNumber(phoneNumber).ifPresent(member -> {
                member.resetLoginFailCount();
                memberRepository.save(member);
            });

            return jwtUtil.generateToken(authentication);
        } catch (AuthenticationException e) {
            memberRepository.findMemberByPhoneNumber(phoneNumber)
                    .ifPresent(member -> {
                        member.increaseLoginFailCount();
                        memberRepository.save(member);
                    });

            throw e;
        }

    }
}
