package com.dev.armond.common.util;

import com.dev.armond.member.service.impl.CustomMemberDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {
    private final CustomMemberDetailsService memberDetailsService;
    private final Key secretKey;
    private final long ACCESS_EXPIRATION_TIME;
    private final long REFRESH_EXPIRATION_TIME;

    public JwtUtil(@Value("${jwt.secret_key}") String secretKey,
                   @Value("${jwt.accessExpiration}") long expirationTime,
                   @Value("${jwt.refreshExpiration}") long refreshExpirationTime,
                   CustomMemberDetailsService memberDetailsService
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.ACCESS_EXPIRATION_TIME = expirationTime;
        this.REFRESH_EXPIRATION_TIME = refreshExpirationTime;
        this.memberDetailsService = memberDetailsService;
    }


    public String generateAccessToken(Long memberId) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + ACCESS_EXPIRATION_TIME);
        String memberIdWithPrefix = AuthUtil.memberIdWithPrefix(memberId);
        log.info("memberIdWithPrefix = {}", memberIdWithPrefix);

        return Jwts.builder()
                .subject(memberIdWithPrefix)
                .issuedAt(new Date())
                .expiration(expiry)
                .signWith(secretKey)
                .compact();
    }

    public String generateRefreshToken(Long memberId) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + REFRESH_EXPIRATION_TIME);

        return Jwts.builder()
                .subject(AuthUtil.memberIdWithPrefix(memberId))
                .issuedAt(now)
                .expiration(expiry)
                .signWith(secretKey)
                .compact();
    }

    public boolean isValidateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        Long memberId = getMemberId(token);
        UserDetails memberDetails = memberDetailsService.loadUserById(memberId);
        return new UsernamePasswordAuthenticationToken(
                memberDetails,
                null,
                memberDetails.getAuthorities()
        );
    }

    public Long getMemberId(String token) {
        String sub = Jwts.parser()
                .verifyWith((SecretKey) secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();

        if (!sub.startsWith("UID:")) {
            throw new IllegalStateException("Invalid subject prefix");
        }

        return Long.parseLong(sub.substring(4));
    }


    private boolean isTokenExpired(String token) {
        Claims claims = Jwts.parser()
                .verifyWith((SecretKey) secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }
}
