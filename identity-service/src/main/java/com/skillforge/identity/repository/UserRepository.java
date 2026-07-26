package com.skillforge.identity.repository;


import com.skillforge.identity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // JpaRepository nous offre déjà save(), findById(), et findAll() gratuitement.
}
