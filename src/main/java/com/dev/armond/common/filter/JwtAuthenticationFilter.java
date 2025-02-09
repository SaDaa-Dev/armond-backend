package com.dev.armond.common.filter;

import com.dev.armond.common.util.JwtUtil;
import com.dev.armond.user.dto.CustomUserDetails;
import com.dev.armond.user.service.UserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private JwtUtil jwtUtil;
    private UserDetailService userDetailService;

    public JwtAuthenticationFilter() {
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authentication");
        String userEmail = null;
        String token = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            userEmail = jwtUtil.extractEmail(token);
        }

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            CustomUserDetails userDetails = userDetailService.loadUserByUsername(userEmail);
            if (jwtUtil.validateToken(token, userDetails.getEmail())) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
