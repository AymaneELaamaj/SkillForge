package com.skillforge.identity.security.service;

import com.skillforge.identity.dto.request.LoginRequest;
import com.skillforge.identity.dto.request.RegisterRequest;
import com.skillforge.identity.dto.response.LoginResponse;
import com.skillforge.identity.dto.response.UserResponse;
import com.skillforge.identity.entity.Role;
import com.skillforge.identity.entity.User;
import com.skillforge.identity.enums.RoleName;
import com.skillforge.identity.exception.EmailAlreadyExistsException;
import com.skillforge.identity.exception.InvalidCredentialsException;
import com.skillforge.identity.exception.UserNotFoundException;
import com.skillforge.identity.exception.UsernameAlreadyExistsException;
import com.skillforge.identity.mapper.UserMapper;
import com.skillforge.identity.security.model.CustomUserDetails;
import com.skillforge.identity.repository.RoleRepository;
import com.skillforge.identity.repository.UserRepository;
import jakarta.transaction.Transactional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    
    public AuthService(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            UserMapper userMapper,
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
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
}
