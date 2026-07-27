package com.skillforge.identity.service;

import com.skillforge.identity.entity.User;
import com.skillforge.identity.exception.EmailAlreadyExistsException;
import com.skillforge.identity.exception.UsernameAlreadyExistsException;
import com.skillforge.identity.exception.UserNotFoundException;
import com.skillforge.identity.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("L'email est déjà utilisé.");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyExistsException("Le nom d'utilisateur est déjà pris.");
        }
        
        // C'est ici que tu hasheras le mot de passe plus tard avec BCrypt
        return userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable avec l'ID: " + id));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long id, User userUpdates) {
        User existingUser = getUser(id);
        
        // Vérification des conflits uniquement si l'email ou l'username change
        if (!existingUser.getEmail().equals(userUpdates.getEmail()) && 
            userRepository.existsByEmail(userUpdates.getEmail())) {
            throw new EmailAlreadyExistsException("L'email est déjà utilisé.");
        }

        existingUser.setEmail(userUpdates.getEmail());
        existingUser.setUsername(userUpdates.getUsername());
        
        return userRepository.save(existingUser);
    }
}