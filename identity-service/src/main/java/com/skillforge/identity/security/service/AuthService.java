package com.skillforge.identity.security.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillforge.identity.dto.request.LoginRequest;
import com.skillforge.identity.dto.request.RegisterRequest;
import com.skillforge.identity.dto.response.LoginResponse;
import com.skillforge.identity.dto.response.UserResponse;
import com.skillforge.identity.entity.OutboxEvent;
import com.skillforge.identity.entity.Role;
import com.skillforge.identity.entity.User;
import com.skillforge.identity.enums.RoleName;
import com.skillforge.identity.event.UserRegisteredEvent;
import com.skillforge.identity.exception.EmailAlreadyExistsException;
import com.skillforge.identity.exception.InvalidCredentialsException;
import com.skillforge.identity.exception.UserNotFoundException;
import com.skillforge.identity.exception.UsernameAlreadyExistsException;
import com.skillforge.identity.mapper.UserMapper;
import com.skillforge.identity.security.model.CustomUserDetails;
import com.skillforge.identity.repository.OutboxEventRepository;
import com.skillforge.identity.repository.RoleRepository;
import com.skillforge.identity.repository.UserRepository;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final String USER_REGISTERED_TOPIC = "user-registered";
    private static final String USER_REGISTERED_EVENT_TYPE = "UserRegisteredEvent";

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final OutboxEventRepository outboxEventRepository;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;
    
    public AuthService(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            UserMapper userMapper,
            UserRepository userRepository,
            RoleRepository roleRepository,
            OutboxEventRepository outboxEventRepository,
            PasswordEncoder passwordEncoder,
            ObjectMapper objectMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.outboxEventRepository = outboxEventRepository;
        this.passwordEncoder = passwordEncoder;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public UserResponse register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.username())) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));

        User user = userMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.password()));

        user.getRoles().add(userRole);

        User savedUser = userRepository.save(user);

        saveUserRegisteredOutboxEvent(savedUser);

        return userMapper.toResponse(savedUser);
    }

    public LoginResponse login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = userRepository.findById(userDetails.getId())
                        .orElseThrow(() -> new UserNotFoundException("User not found"));
            String token = jwtService.generateToken(userDetails);
            UserResponse userResponse = userMapper.toResponse(user);

            return LoginResponse.of(token, jwtService.getExpirationMs() / 1000, userResponse);
        } catch (BadCredentialsException ex) {
            throw new InvalidCredentialsException();
        }
    }

    public UserResponse me(Authentication authentication) {

        CustomUserDetails principal =
                (CustomUserDetails) authentication.getPrincipal();

        User user = userRepository.findById(principal.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return userMapper.toResponse(user);
    }

    private void saveUserRegisteredOutboxEvent(User user) {
        Instant registeredAt = Instant.now();
        UserRegisteredEvent event = new UserRegisteredEvent(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                mapRoleNames(user.getRoles()),
                registeredAt
        );

        outboxEventRepository.save(new OutboxEvent(
                USER_REGISTERED_TOPIC,
                user.getId().toString(),
                USER_REGISTERED_EVENT_TYPE,
                toJson(event),
                registeredAt
        ));
    }

    private Set<String> mapRoleNames(Set<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return Set.of();
        }

        return roles.stream()
                .map(Role::getName)
                .map(Enum::name)
                .collect(Collectors.toUnmodifiableSet());
    }

    private String toJson(UserRegisteredEvent event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException("Failed to serialize UserRegisteredEvent", ex);
        }
    }
}
