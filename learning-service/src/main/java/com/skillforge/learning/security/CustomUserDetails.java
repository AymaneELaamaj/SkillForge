package com.skillforge.learning.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final Long id;
    private final String username;
    private final String email;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(
            Long id,
            String username,
            String email,
            List<String> roles) {

        this.id = id;
        this.username = username;
        this.email = email;

        this.authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null; // Le Learning Service ne connaît pas le mot de passe
    }

    @Override
    public String getUsername() {
        return username;
    }
}