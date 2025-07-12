package com.dev.armond.common.exception;

import com.dev.armond.common.enums.ErrorCode;

public class InvalidCredentialsException extends AuthenticationException {
    public InvalidCredentialsException() {
        super(ErrorCode.AUTH_INVALID_CREDENTIALS);
    }
    
    public InvalidCredentialsException(String customMessage) {
        super(ErrorCode.AUTH_INVALID_CREDENTIALS, customMessage);
    }
} 