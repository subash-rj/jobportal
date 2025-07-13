package com.example.jobportal.controller;

import com.example.jobportal.Role.ERole;
import com.example.jobportal.dto.AuthResponseDTO;
import com.example.jobportal.dto.LoginRequestDTO;
import com.example.jobportal.dto.SignupDTO;
import com.example.jobportal.model.Role;
import com.example.jobportal.model.User;
import com.example.jobportal.repository.RoleRepository;
import com.example.jobportal.repository.UserRepository;
import com.example.jobportal.security.JwtUtils;
import com.example.jobportal.security.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;


    public AuthController(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupDTO signupDTO) {
        if (userRepository.findByEmail(signupDTO.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body("Error:Email is already in use");
        }

        Set<String> strRoles = signupDTO.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_CANDIDATE)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role.toLowerCase()) {
                    case "role_recruiter":
                        roles.add(roleRepository.findByName(ERole.ROLE_RECRUITER)
                                .orElseThrow(() -> new RuntimeException("Error: Role not found.")));
                        break;
                    case "role_admin":
                        roles.add(roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role not found.")));
                        break;
                    default:
                        roles.add(roleRepository.findByName(ERole.ROLE_CANDIDATE)
                                .orElseThrow(() -> new RuntimeException("Error: Role not found.")));
                        break;
                }
            });
        }

        User user = new User(
                signupDTO.getName(),
                signupDTO.getEmail(),
                passwordEncoder.encode(signupDTO.getPassword())
        );
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok("User Registered");
    }

    @PostMapping("/signing")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Print only safe fields
        //System.out.println("Username: " + userDetails.getUsername());
        //System.out.println("Authorities: " + userDetails.getAuthorities().stream()
                //.map(GrantedAuthority::getAuthority).toList());

        String jwt = jwtUtils.generateJwtToken(userDetails.getUsername());
        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("CANDIDATE");

        return ResponseEntity.ok(new AuthResponseDTO(jwt, userDetails.getUsername(), role));
    }


}
