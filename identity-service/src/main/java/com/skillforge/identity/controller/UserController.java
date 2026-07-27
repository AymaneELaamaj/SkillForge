package com.skillforge.identity.controller;

import com.skillforge.identity.dto.request.CreateUserRequest;
import com.skillforge.identity.dto.request.UpdateUserRequest;
import com.skillforge.identity.dto.response.UserResponse;
import com.skillforge.identity.entity.User;
import com.skillforge.identity.mapper.UserMapper;
import com.skillforge.identity.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        User userToCreate = userMapper.toEntity(request);
        User savedUser = userService.createUser(userToCreate);
        return new ResponseEntity<>(userMapper.toResponse(savedUser), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        return ResponseEntity.ok(userMapper.toResponse(user));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> responseList = userService.getAllUsers().stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id, 
            @Valid @RequestBody UpdateUserRequest request) {
            
        // Pour la mise à jour, on crée une entité temporaire depuis la requête
        User userUpdates = new User();
        userUpdates.setUsername(request.username());
        userUpdates.setEmail(request.email());
        
        User updatedUser = userService.updateUser(id, userUpdates);
        
        return ResponseEntity.ok(userMapper.toResponse(updatedUser));
    }
}