package com.example.contactmanager.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntime(RuntimeException ex) {
        // This log will automatically include the userId and correlationId from MDC!
        log.error("SYSTEM ERROR DETECTED: {}", ex.getMessage());
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }
}