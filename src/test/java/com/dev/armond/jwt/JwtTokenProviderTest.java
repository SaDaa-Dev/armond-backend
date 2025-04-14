package com.dev.armond.jwt;

import com.dev.armond.common.util.JwtUtil;
import com.dev.armond.member.dto.CustomMemberDetails;
import com.dev.armond.member.service.impl.CustomMemberDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.security.core.Authentication;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class JwtTokenProviderTest {

    private JwtUtil jwtUtil;
    private CustomMemberDetailsService memberDetailsService;
    private Logger log;
    private final String SECRET = "VGhpcy1pcy1hLXN1cGVyLWxvbmcta2V5LXRlc3QtZm9yLUpXVC1zaWdu"; // base64
    private final long ACCESS_EXP = 1000 * 60 * 15; // 15분
    private final long REFRESH_EXP = 1000 * 60 * 60 * 24 * 7; // 7일
    private final Long MEMBER_ID = 1L;

    @BeforeEach
    void setUp() {
        // ① UserDetailsService Mock
        memberDetailsService = Mockito.mock(CustomMemberDetailsService.class);
        CustomMemberDetails stub =
                new CustomMemberDetails(MEMBER_ID, "01012345678", "pw", Collections.emptyList());
        Mockito.when(memberDetailsService.loadUserByUsername(String.valueOf(MEMBER_ID)))
                .thenReturn(stub);

        // ② JwtUtil 생성
        jwtUtil = new JwtUtil(SECRET, ACCESS_EXP, REFRESH_EXP, memberDetailsService);
    }

    @Test
    @DisplayName("AccessToken 생성 & 유효성 검증 & memberId 추출")
    void generate_and_validate_access_token() {
        String access = jwtUtil.generateAccessToken(MEMBER_ID);

        assertThat(jwtUtil.isValidateToken(access)).isTrue();
        assertThat(jwtUtil.getMemberId(access)).isEqualTo(MEMBER_ID);
    }

    @Test
    @DisplayName("RefreshToken 생성 & 유효성 검증")
    void generate_and_validate_refresh_token() {
        String refresh = jwtUtil.generateRefreshToken(MEMBER_ID);

        assertThat(jwtUtil.isValidateToken(refresh)).isTrue();
        assertThat(jwtUtil.getMemberId(refresh)).isEqualTo(MEMBER_ID);
    }

    @Test
    @DisplayName("Authentication 객체 생성 성공")
    void authentication_from_token() {
        String access = jwtUtil.generateAccessToken(MEMBER_ID);

        Authentication auth = jwtUtil.getAuthentication(access);

        assertThat(auth.isAuthenticated()).isTrue();
        assertThat(auth.getName()).isEqualTo(String.valueOf(MEMBER_ID));
    }

    @Test
    @DisplayName("잘못된 토큰은 validate=false & 예외 발생")
    void invalid_token_should_fail() {
        String invalid = "Bearer.invalid.token";
    }
}