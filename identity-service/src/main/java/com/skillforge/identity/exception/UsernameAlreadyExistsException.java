package com.skillforge.identity.exception;

public class UsernameAlreadyExistsException extends RuntimeException {

    private final String username;

    public UsernameAlreadyExistsException(String username) {
        super("Username '" + username + "' already exists.");
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}