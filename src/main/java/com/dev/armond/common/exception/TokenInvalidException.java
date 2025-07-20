package com.dev.armond.common.exception;

import com.dev.armond.common.enums.ErrorCode;

public class TokenInvalidException extends AuthenticationException {
    public TokenInvalidException() {
        super(ErrorCode.AUTH_TOKEN_INVALID);
    }
    
    public TokenInvalidException(String customMessage) {
        super(ErrorCode.AUTH_TOKEN_INVALID, customMessage);
    }
} 