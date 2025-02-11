package com.dev.armond.jwt;

import com.dev.armond.common.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {
    private JwtUtil jwtUtil;
    private final String secretKey = "uMWSG55ZD61gPsgB9U8PvTUVRXBG2wOvI/U70I++6DE=";
    private final long expirationTime = 3600000;

    @BeforeEach
    void setup() {
        jwtUtil = new JwtUtil(secretKey, expirationTime);
    }

    @Test
    void testGenerateAndExtractEmail() {
        String email = "test@example.com";
        String token = jwtUtil.generateToken(email);
        assertNotNull(token, "Token should not be null");

        String extractedEmail = jwtUtil.extractEmail(token);
        assertEquals(email, extractedEmail, "Extracted email should match the original email");
    }

    @Test
    void testValidateToken() {
        String email = "user@example.com";
        String token = jwtUtil.generateToken(email);

        // 올바른 이메일로 검증 시 true 반환
        assertTrue(jwtUtil.validateToken(token, email), "Token should be valid for the correct email");
        // 잘못된 이메일로 검증 시 false 반환
        assertFalse(jwtUtil.validateToken(token, "other@example.com"), "Token should be invalid for a different email");
    }

    @Test
    void testTokenExpiration() throws InterruptedException {
        // 만료시간을 1초로 설정한 JwtUtil 인스턴스 생성
        long shortExpiration = 1000; // 1초
        JwtUtil shortLivedJwtUtil = new JwtUtil(secretKey, shortExpiration);
        String email = "expire@example.com";
        String token = shortLivedJwtUtil.generateToken(email);

        // 2초 대기하여 토큰이 만료되도록 함
        Thread.sleep(2000);

        // 만료된 토큰은 validateToken()에서 false를 반환해야 함
        assertFalse(shortLivedJwtUtil.validateToken(token, email), "Token should be expired");
    }
}