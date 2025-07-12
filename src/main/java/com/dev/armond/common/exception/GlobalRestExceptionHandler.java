package com.dev.armond.common.exception;

import com.dev.armond.common.enums.ErrorCode;
import com.dev.armond.common.reponse.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalRestExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleUserNotFoundException(UserNotFoundException e) {
        log.warn("User not found: {}", e.getMessage());
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(HttpStatus.valueOf(errorCode.getHttpStatus()))
                .body(ApiResponse.fail("로그인 실패", e.getMessage(), errorCode.getCode()));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidCredentialsException(InvalidCredentialsException e) {
        log.warn("Invalid credentials: {}", e.getMessage());
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(HttpStatus.valueOf(errorCode.getHttpStatus()))
                .body(ApiResponse.fail("로그인 실패", e.getMessage(), errorCode.getCode()));
    }

    @ExceptionHandler(TokenGenerationException.class)
    public ResponseEntity<ApiResponse<Object>> handleTokenGenerationException(TokenGenerationException e) {
        log.error("Token generation failed: {}", e.getMessage(), e);
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(HttpStatus.valueOf(errorCode.getHttpStatus()))
                .body(ApiResponse.error("서버 오류", e.getMessage(), errorCode.getCode()));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<Object>> handleAuthenticationException(AuthenticationException e) {
        log.warn("Authentication failed: {}", e.getMessage());
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(HttpStatus.valueOf(errorCode.getHttpStatus()))
                .body(ApiResponse.fail("인증 실패", e.getMessage(), errorCode.getCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        log.warn("Validation failed: {}", errors);
        ErrorCode errorCode = ErrorCode.VALIDATION_FAILED;
        return ResponseEntity.status(HttpStatus.valueOf(errorCode.getHttpStatus()))
                .body(ApiResponse.fail("입력 검증 실패", errors.toString(), errorCode.getCode()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> handleRuntimeException(RuntimeException e) {
        log.error("Runtime exception occurred: {}", e.getMessage(), e);
        ErrorCode errorCode = ErrorCode.COMMON_RUNTIME_ERROR;
        return ResponseEntity.status(HttpStatus.valueOf(errorCode.getHttpStatus()))
                .body(ApiResponse.error("서버 오류", e.getMessage(), errorCode.getCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleAllExceptions(Exception e) {
        log.error("Unexpected exception occurred: {}", e.getMessage(), e);
        ErrorCode errorCode = ErrorCode.SYSTEM_INTERNAL_ERROR;
        return ResponseEntity.status(HttpStatus.valueOf(errorCode.getHttpStatus()))
                .body(ApiResponse.error("서버 오류", "예기치 못한 오류가 발생했습니다.", errorCode.getCode()));
    }
}
