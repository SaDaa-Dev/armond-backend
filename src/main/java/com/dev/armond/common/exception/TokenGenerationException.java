package com.dev.armond.common.exception;

import com.dev.armond.common.enums.ErrorCode;

public class TokenGenerationException extends AuthenticationException {
    public TokenGenerationException() {
        super(ErrorCode.AUTH_TOKEN_GENERATION_FAILED);
    }
    
    public TokenGenerationException(String customMessage) {
        super(ErrorCode.AUTH_TOKEN_GENERATION_FAILED, customMessage);
    }
    
    public TokenGenerationException(String customMessage, Throwable cause) {
        super(ErrorCode.AUTH_TOKEN_GENERATION_FAILED, customMessage, cause);
    }
}