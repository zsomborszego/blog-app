package com.chat.blog.api;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ControllerExpcetionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", BAD_REQUEST.value());
        response.put("errors", errors);
        return ResponseEntity.status(BAD_REQUEST).body(response);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEntityNotFoundException(EntityNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", NOT_FOUND.value());
        response.put("errors", ex.getMessage());
        return ResponseEntity.status(NOT_FOUND).body(response);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<Map<String, Object>> handleEntityExistsException(EntityExistsException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", CONFLICT.value());
        response.put("errors", ex.getMessage());
        return ResponseEntity.status(CONFLICT).body(response);
    }

}
