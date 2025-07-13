package com.example.jobportal.controller;

import com.example.jobportal.dto.UserRequestDTO;
import com.example.jobportal.model.Role;
import com.example.jobportal.model.User;
import com.example.jobportal.repository.UserRepository;
import com.example.jobportal.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private final UserRepository userRepository;

    public UserController(UserService userService,UserRepository userRepository){
        this.userService = userService;
        this.userRepository= userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody UserRequestDTO userRequestDTO){
        User savedUser = userService.registerUser(userRequestDTO);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);

    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                     .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(Map.of(
                "id",user.getId(),
                "name",user.getName(),
                "email",user.getEmail(),
                "roles",user.getRoles().stream()
                        .map(Role::getName).collect(Collectors.toSet())
        ));

    }

}
