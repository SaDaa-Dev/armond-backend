package com.dev.armond.member.service.impl;

import com.dev.armond.common.util.JwtUtil;
import com.dev.armond.member.dto.CustomMemberDetails;
import com.dev.armond.member.dto.TokenResponse;
import com.dev.armond.member.entity.Member;
import com.dev.armond.member.entity.RefreshToken;
import com.dev.armond.member.repository.MemberRepository;
import com.dev.armond.member.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
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
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenResponse createTokenForMember(Member member) {
        Long memberId = member.getId();
        String accessToken = jwtUtil.generateAccessToken(memberId);
        String refreshToken = jwtUtil.generateRefreshToken(memberId);

        refreshTokenRepository.save(
                RefreshToken.builder()
                        .memberId(memberId)
                        .token(refreshToken)
                        .build()
        );

        return new TokenResponse(accessToken, refreshToken);
    }

    public TokenResponse login(String phoneNumber, String password) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(phoneNumber, password)
            );

            CustomMemberDetails memberDetails = (CustomMemberDetails) auth.getPrincipal();
            String pn = memberDetails.getPhoneNumber();
            Long memberId = memberDetails.getMemberId();

            memberRepository.findMemberByPhoneNumber(pn).ifPresent(member -> {
                member.resetLoginFailCount();
                memberRepository.save(member);
            });

            String accessToken = jwtUtil.generateAccessToken(memberId);
            String refreshToken = jwtUtil.generateRefreshToken(memberId);

            refreshTokenRepository.save(
                    RefreshToken.builder()
                            .memberId(memberId)
                            .token(refreshToken)
                            .build()
            );

            return new TokenResponse(accessToken, refreshToken);
        } catch (AuthenticationException e) {
            memberRepository.findMemberByPhoneNumber(phoneNumber)
                    .ifPresent(member -> {
                        member.increaseLoginFailCount();
                        memberRepository.save(member);
                    });

            throw e;
        }

    }

    public TokenResponse reissue(String refreshToken) {
        if (!jwtUtil.isValidateToken(refreshToken)) {
            throw new RuntimeException("Invalid Refresh Token");
        }

        Long memberId = jwtUtil.getMemberId(refreshToken);

        RefreshToken savedToken = refreshTokenRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Refresh Token Not Found"));

        if (!savedToken.getToken().equals(refreshToken)) {
            throw new RuntimeException("Token Mismatch");
        }

        String newAccessToken = jwtUtil.generateAccessToken(memberId);
        String newRefreshToken = jwtUtil.generateRefreshToken(memberId);

        savedToken.updateToken(newRefreshToken);
        refreshTokenRepository.save(savedToken);

        return new TokenResponse(newAccessToken, newRefreshToken);
    }


    @Transactional
    public void logout(Long memberId) {
        refreshTokenRepository.deleteById(memberId);
    }

}
