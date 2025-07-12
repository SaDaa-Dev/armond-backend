package com.dev.armond.member.service.impl;

import com.dev.armond.common.exception.InvalidCredentialsException;
import com.dev.armond.common.exception.TokenGenerationException;
import com.dev.armond.common.exception.UserNotFoundException;
import com.dev.armond.common.util.JwtTokenProvider;
import com.dev.armond.member.dto.LoginRequestDto;
import com.dev.armond.member.dto.MemberInfo;
import com.dev.armond.member.dto.TokenDto;
import com.dev.armond.member.entity.Member;
import com.dev.armond.member.entity.RefreshToken;
import com.dev.armond.member.repository.MemberRepository;
import com.dev.armond.member.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;

    @Value("${jwt.refreshExpiration}")
    private long refreshTokenExpiration;

    public TokenDto login(LoginRequestDto loginRequestDto) {
        try {
            log.info("Login attempt for member: {}", loginRequestDto.getMemberName());
            
            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(loginRequestDto.getMemberName(), loginRequestDto.getPassword());

            Authentication authentication;
            try {
                authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            } catch (BadCredentialsException e) {
                log.warn("Invalid credentials for member: {}", loginRequestDto.getMemberName());
                throw new InvalidCredentialsException("아이디 또는 비밀번호가 올바르지 않습니다.");
            } catch (AuthenticationException e) {
                log.error("Authentication failed for member: {}", loginRequestDto.getMemberName(), e);
                throw new InvalidCredentialsException("인증에 실패했습니다.");
            }

            String accessToken;
            String refreshToken;
            try {
                accessToken = jwtTokenProvider.createAccessToken(authentication);
                refreshToken = jwtTokenProvider.createRefreshToken();
            } catch (Exception e) {
                log.error("Token generation failed for member: {}", loginRequestDto.getMemberName(), e);
                throw new TokenGenerationException("토큰 생성에 실패했습니다.");
            }

            try {
                RefreshToken refreshTokenEntity = RefreshToken.builder()
                        .id(authentication.getName())
                        .refreshToken(refreshToken)
                        .expiration(refreshTokenExpiration / 1000)
                        .build();

                refreshTokenRepository.save(refreshTokenEntity);
            } catch (Exception e) {
                log.error("Refresh token save failed for member: {}", loginRequestDto.getMemberName(), e);
                throw new TokenGenerationException("리프레시 토큰 저장에 실패했습니다.");
            }

            Member member = memberRepository.findMemberByPhoneNumber(authentication.getName())
                    .orElseThrow(() -> {
                        log.error("Member not found after authentication: {}", authentication.getName());
                        return new UserNotFoundException("인증 후 사용자 정보를 찾을 수 없습니다.");
                    });

            log.info("Login successful for member: {}", loginRequestDto.getMemberName());
            return TokenDto.builder()
                    .grantType("Bearer")
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .accessTokenExpiresIn(jwtTokenProvider.getAccessTokenValidityIn())
                    .memberInfo(MemberInfo.from(member))
                    .build();
        } catch (com.dev.armond.common.exception.AuthenticationException e) {
            // 이미 우리가 정의한 예외는 다시 던짐
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error during login for member: {}", loginRequestDto.getMemberName(), e);
            throw new RuntimeException("로그인 처리 중 서버 오류가 발생했습니다.");
        }
    }

    public TokenDto reissue(String accessToken, String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new RuntimeException("Refresh Token이 유효하지 않습니다.");
        }

        String memberName = jwtTokenProvider.getMemberNameFromToken(accessToken);

        RefreshToken storedRefreshToken = refreshTokenRepository.findById(memberName)
                .orElseThrow(() -> new RuntimeException("로그아웃되었거나 만료된 사용자입니다."));

        if (!storedRefreshToken.getRefreshToken().equals(refreshToken)) {
            throw new RuntimeException("Refresh Token이 일치하지 않습니다. 보안 위험이 있을 수 있습니다.");
        }

        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        String newAccessToken = jwtTokenProvider.createAccessToken(authentication);
        String newRefreshToken = jwtTokenProvider.createRefreshToken();

        storedRefreshToken.updateToken(newRefreshToken, refreshTokenExpiration);
        refreshTokenRepository.save(storedRefreshToken);

        Member member = memberRepository.findMemberByPhoneNumber(memberName)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        return TokenDto.builder()
                .grantType("Bearer")
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .accessTokenExpiresIn(jwtTokenProvider.getAccessTokenValidityIn())
                .memberInfo(MemberInfo.from(member))
                .build();
    }

    @Transactional
    public void logout(String accessToken) {
        String memberName = jwtTokenProvider.getMemberNameFromToken(accessToken);

        if (refreshTokenRepository.existsById(memberName)) {
            refreshTokenRepository.deleteById(memberName);
        }
    }

}
