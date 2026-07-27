package com.skillforge.learning.exception;

public class IdentityServiceUnavailableException extends RuntimeException {
    public IdentityServiceUnavailableException(String message) {
        super(message);
    }
}