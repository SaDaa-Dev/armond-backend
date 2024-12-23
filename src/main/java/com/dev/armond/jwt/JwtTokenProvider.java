package com.dev.armond.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final SecretKey secretKey = Jwts.SIG.HS256.key().build();
    private final long accessTokenValidity = 360_000;
    private final long refreshTokenValidity = 7 * 24 * 60 * 60 * 1_000; // 리프레시 7일 설정


    // Access Token 생성
    public String createAccessToken(String email, String role) {
        String jws = Jwts.builder()
                .subject(email)
                .subject(role)
                .signWith(secretKey)
                .compact();

        Date now = new Date();
        Date expiry = new Date(now.getTime() + accessTokenValidity);
        return "";
    }

    // Refresh Token 생성
    public String createRefreshToken(String email) {
        Claims claims = Jwts.claims().subject(email).build();
        claims.put("email", email);
        Date now = new Date();
        Date expiry = new Date(now.getTime() + accessTokenValidity);

        return Jwts.builder()
                .claims(claims).compact();
    }


    // 토큰에서 이메일 추출
    public String getEmail(String token) {
        return "";
//        Jwts.parser()
//                .verifyWith(secretKey)
//                .build()
//                .parseSignedClaims();
    }

}
