package com.smartcheck.exception;

public class ResourceNotAuthorizedException extends RuntimeException {
    public ResourceNotAuthorizedException(String message) {
        super(message);
    }
}
