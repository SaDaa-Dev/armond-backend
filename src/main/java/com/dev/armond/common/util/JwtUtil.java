package com.dev.armond.common.util;

import com.dev.armond.member.service.impl.CustomMemberDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
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


    public String generateAccessToken(String phoneNumber) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + ACCESS_EXPIRATION_TIME);

        return Jwts.builder()
                .subject(phoneNumber)
                .issuedAt(new Date())
                .expiration(expiry)
                .signWith(secretKey)
                .compact();
    }

    public String generateRefreshToken(String phoneNumber) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + REFRESH_EXPIRATION_TIME);

        return Jwts.builder()
                .subject(phoneNumber)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
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
        String phoneNumber = getPhoneNumber(token);
        UserDetails memberDetails = memberDetailsService.loadUserByUsername(phoneNumber);
        return new UsernamePasswordAuthenticationToken(
                memberDetails,
                null,
                memberDetails.getAuthorities()
        );
    }

    public String getPhoneNumber(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
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
