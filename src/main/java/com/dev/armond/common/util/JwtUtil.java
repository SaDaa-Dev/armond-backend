package com.dev.armond.common.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtUtil {
    private final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private static final String AUTHORITIES_KEY = "auth";
//    private static final String BEARER_TYPE = "Bearer";

    // Jwt 관련 yml 변수들
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.accessExpiration}")
    private long ACCESS_EXPIRATION_TIME;
    @Value("${jwt.refreshExpiration}")
    private long REFRESH_EXPIRATION_TIME;

    private Key key;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }


    public String createAccessToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validDate = new Date(now + ACCESS_EXPIRATION_TIME);

        return Jwts.builder()
                .subject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key)
                .expiration(validDate)
                .compact();
    }

    public String createRefreshToken() {
        long now = (new Date()).getTime();
        Date validDate = new Date(now + REFRESH_EXPIRATION_TIME);

        return Jwts.builder()
                .expiration(validDate)
                .signWith(key)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Long memberId = getMemberId(token);
        memberDetailsService.loadUserById(memberId);
        UserDetails memberDetails = memberDetailsService.loadUserById(memberId);
        return new UsernamePasswordAuthenticationToken(
                memberDetails,
                null,
                memberDetails.getAuthorities()
        );
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
