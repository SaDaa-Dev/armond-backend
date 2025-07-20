package com.dev.armond.common.filter;

import com.dev.armond.common.enums.ErrorCode;
import com.dev.armond.common.exception.TokenExpiredException;
import com.dev.armond.common.exception.TokenInvalidException;
import com.dev.armond.common.reponse.ApiResponse;
import com.dev.armond.common.util.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 인증이 필요 없는 경로들
    private static final String[] WHITELIST_PATTERNS = {
            "/auth/login",
            "/auth/register", 
            "/auth/reissue"
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
            throws ServletException, IOException {
        
        // 화이트리스트 경로는 토큰 검증 없이 통과
        if (isWhitelistPath(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = resolveToken(request);

        if (StringUtils.hasText(token)) {
            try {
                // 토큰 유효성 검증 (예외 발생 가능)
                jwtTokenProvider.validateTokenWithException(token);
                
                // 토큰이 유효하면 Authentication 객체를 SecurityContext에 저장
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
            } catch (TokenExpiredException e) {
                log.warn("토큰 만료 요청: {}", request.getRequestURI());
                sendErrorResponse(response, e.getErrorCode(), "토큰이 만료되었습니다. 재발급이 필요합니다.");
                return;
                
            } catch (TokenInvalidException e) {
                log.warn("유효하지 않은 토큰 요청: {}", request.getRequestURI());
                sendErrorResponse(response, e.getErrorCode(), "유효하지 않은 토큰입니다.");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isWhitelistPath(String requestURI) {
        for (String pattern : WHITELIST_PATTERNS) {
            if (requestURI.startsWith(pattern)) {
                return true;
            }
        }
        return false;
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private void sendErrorResponse(HttpServletResponse response, ErrorCode errorCode, String message) 
            throws IOException {
        response.setStatus(errorCode.getHttpStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        ApiResponse<Object> errorResponse = ApiResponse.fail(
            "인증 실패", 
            message, 
            errorCode.getCode()
        );

        String jsonResponse = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(jsonResponse);
    }
}
