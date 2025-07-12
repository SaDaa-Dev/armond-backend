package com.dev.armond.common.exception;

import com.dev.armond.common.enums.ErrorCode;

public class UserNotFoundException extends AuthenticationException {
    public UserNotFoundException() {
        super(ErrorCode.AUTH_USER_NOT_FOUND);
    }
    
    public UserNotFoundException(String customMessage) {
        super(ErrorCode.AUTH_USER_NOT_FOUND, customMessage);
    }
} 