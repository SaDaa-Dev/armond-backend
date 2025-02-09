package com.dev.armond.jwt;

import com.dev.armond.common.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.lang.reflect.Field;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class JwtTokenProviderTest {
    private final JwtUtil jwtTokenProvider = new JwtUtil();
    private SecretKey getSecretKey() {
        try {
            Field secretKeyField = JwtUtil.class.getDeclaredField("SECRET_KEY");
            secretKeyField.setAccessible(true);
            return (SecretKey) secretKeyField.get(jwtTokenProvider);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGenerateToken() {
        String email = "test@example.com";
        String token = jwtTokenProvider.generateToken(email);
        assertNotNull(token, "토큰은 null 이 아니어야 합니다.");

        // 토큰 파싱하여 subject (email)이 올바른지 확인
        Claims claims = Jwts.parser()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        assertEquals(email, claims.getSubject(), "토큰의 subject는 입력한 이메일과 동일해야 합니다.");
        assertTrue(claims.getExpiration().after(new Date()), "토큰 만료시간은 현재 시간 이후여야 합니다.");
    }
}