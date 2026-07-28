package com.skillforge.identity.service;

import com.skillforge.identity.entity.Role;
import com.skillforge.identity.entity.User;
import com.skillforge.identity.enums.RoleName;
import com.skillforge.identity.exception.EmailAlreadyExistsException;
import com.skillforge.identity.exception.UsernameAlreadyExistsException;
import com.skillforge.identity.exception.UserNotFoundException;
import com.skillforge.identity.repository.RoleRepository;
import com.skillforge.identity.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException(user.getEmail());
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyExistsException(user.getUsername());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new IllegalStateException("Le rôle ROLE_USER est introuvable."));
        user.setRoles(Set.of(userRole));

        return userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable avec l'ID: " + id));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User updateUser(Long id, User userUpdates) {
        User existingUser = getUser(id);

        if (!existingUser.getEmail().equals(userUpdates.getEmail())
                && userRepository.existsByEmail(userUpdates.getEmail())) {
            throw new EmailAlreadyExistsException(userUpdates.getEmail());
        }

        if (!existingUser.getUsername().equals(userUpdates.getUsername())
                && userRepository.existsByUsername(userUpdates.getUsername())) {
            throw new UsernameAlreadyExistsException(userUpdates.getUsername());
        }

        existingUser.setEmail(userUpdates.getEmail());
        existingUser.setUsername(userUpdates.getUsername());

        return userRepository.save(existingUser);
    }
}
