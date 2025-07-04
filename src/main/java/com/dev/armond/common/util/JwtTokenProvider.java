package com.dev.armond.common.util;

import com.dev.armond.member.service.impl.CustomMemberDetailsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
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
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    private final CustomMemberDetailsService memberDetailsService;

    private static final String AUTHORITIES_KEY = "auth";
//    private static final String BEARER_TYPE = "Bearer";

    // Jwt 관련 yml 변수들
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.accessExpiration}")
    private long ACCESS_EXPIRATION_TIME;
    @Value("${jwt.refreshExpiration}")
    private long REFRESH_EXPIRATION_TIME;

    private SecretKey key;

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
        String memberName = getMemberNameFromToken(token);
        UserDetails memberDetails = memberDetailsService.loadUserByUsername(memberName);
        return new UsernamePasswordAuthenticationToken(
                memberDetails,
                null,
                memberDetails.getAuthorities()
        );
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.", e);
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.", e);
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.", e);
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.", e);
        }
        return false;
    }

    public String getMemberNameFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public long getAccessTokenValidityIn() {
        return ACCESS_EXPIRATION_TIME;
    }
}
