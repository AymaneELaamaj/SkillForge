package com.skillforge.identity.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users") 
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;
    private String password;

    // Constructeurs
    public User() {}

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    // Getters et Setters
    
}
