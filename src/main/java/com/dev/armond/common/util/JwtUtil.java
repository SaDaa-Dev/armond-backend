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
    private final long EXPIRATION_TIME; // 24시간

    public JwtUtil(@Value("${jwt.secret_key}") String secretKey,
                   @Value("${jwt.expiration}") long expirationTime,
                   CustomMemberDetailsService memberDetailsService
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.EXPIRATION_TIME = expirationTime;
        this.memberDetailsService = memberDetailsService;
    }


    public String generateToken(Authentication authentication) {
        String phoneNumber = authentication.getName();

        return Jwts.builder()
                .subject(phoneNumber)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        String phoneNumber = getPhoneNumber(token);
        UserDetails memberDetails = memberDetailsService.loadUserByUsername(phoneNumber);
        return new UsernamePasswordAuthenticationToken(memberDetails, "", memberDetails.getAuthorities());
    }

    public String getPhoneNumber(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) secretKey)
                .build()
                .parseSignedClaims(token).getPayload()
                .getSubject();
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
