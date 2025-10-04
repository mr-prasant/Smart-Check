package com.smartcheck.exception;

import javax.naming.AuthenticationException;

public class JWTTokenExpiredException extends AuthenticationException {
    public JWTTokenExpiredException(String message) {
        super(message);
    }
}
