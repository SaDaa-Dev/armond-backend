package com.dev.armond.common.exception;

import com.dev.armond.common.enums.ErrorCode;

public class TokenExpiredException extends AuthenticationException {
    public TokenExpiredException() {
        super(ErrorCode.AUTH_TOKEN_EXPIRED);
    }
    
    public TokenExpiredException(String customMessage) {
        super(ErrorCode.AUTH_TOKEN_EXPIRED, customMessage);
    }
} 