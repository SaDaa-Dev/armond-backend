package com.dev.armond.common.exception;

import com.dev.armond.common.reponse.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalRestExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleAllExceptions(Exception e) {
        return ResponseEntity.status(500).body(ApiResponse.error("Server Error", e.getMessage()));
    }
}
