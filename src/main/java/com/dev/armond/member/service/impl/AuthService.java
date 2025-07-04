package com.dev.armond.member.service.impl;

import com.dev.armond.common.util.JwtTokenProvider;
import com.dev.armond.member.dto.LoginRequestDto;
import com.dev.armond.member.dto.TokenDto;
import com.dev.armond.member.entity.RefreshToken;
import com.dev.armond.member.repository.MemberRepository;
import com.dev.armond.member.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${jwt.refreshExpiration}")
    private long refreshTokenExpiration;

    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;

    public TokenDto login(LoginRequestDto loginRequestDto) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginRequestDto.getMemberName(), loginRequestDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        String accessToken = jwtTokenProvider.createAccessToken(authentication);
        String refreshToken = jwtTokenProvider.createRefreshToken();

        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .id(authentication.getName())
                .refreshToken(refreshToken)
                .expiration(refreshTokenExpiration / 1000)
                .build();

        refreshTokenRepository.save(refreshTokenEntity);

        return TokenDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiresIn(jwtTokenProvider.getAccessTokenValidityIn())
                .build();


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

        return TokenDto.builder()
                .grantType("Bearer")
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .accessTokenExpiresIn(jwtTokenProvider.getAccessTokenValidityIn())
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
