package com.skillforge.identity.exception;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException() {
        super("Token JWT invalide.");
    }

    public InvalidTokenException(String message) {
        super(message);
    }
}
