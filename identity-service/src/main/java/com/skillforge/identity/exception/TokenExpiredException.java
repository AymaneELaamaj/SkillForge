package com.skillforge.identity.exception;

public class TokenExpiredException extends RuntimeException {

    public TokenExpiredException() {
        super("Le token JWT a expiré.");
    }

    public TokenExpiredException(String message) {
        super(message);
    }
}
