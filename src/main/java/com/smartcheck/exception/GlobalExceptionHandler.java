package com.smartcheck.exception;

import com.smartcheck.dto.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        ExceptionResponse response = new ExceptionResponse(
                HttpStatus.BAD_REQUEST.value(),
                errorMessage
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JWTTokenExpiredException.class)
    public ResponseEntity<Map<String, String>> handleJWTTokenExpiredException(JWTTokenExpiredException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("error", "JWT token expired");
        body.put("reason", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotFound(ResourceNotFoundException ex) {
        ExceptionResponse response = new ExceptionResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotAuthorizedException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotAuthorizedException(ResourceNotAuthorizedException ex) {
        ExceptionResponse response = new ExceptionResponse(
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NoUniqueResourceException.class)
    public ResponseEntity<ExceptionResponse> handleNoUniqueUserException(NoUniqueResourceException ex) {
        ExceptionResponse response = new ExceptionResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceExpiredException.class)
    public ResponseEntity<ExceptionResponse> handleResourceExpiredException(ResourceExpiredException ex) {
        ExceptionResponse response = new ExceptionResponse(
                HttpStatus.FORBIDDEN.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGlobalException(Exception ex) {
        ExceptionResponse response = new ExceptionResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

