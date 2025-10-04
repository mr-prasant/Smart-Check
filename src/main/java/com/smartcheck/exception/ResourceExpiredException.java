package com.smartcheck.exception;

public class ResourceExpiredException extends RuntimeException {
    public ResourceExpiredException(String message) {
        super(message);
    }
}
