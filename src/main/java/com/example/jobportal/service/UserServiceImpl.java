package com.example.jobportal.service;

import com.example.jobportal.Role.ERole;
import com.example.jobportal.dto.UserRequestDTO;
import com.example.jobportal.exception.EmailAlreadyExistsException;
import com.example.jobportal.model.Role;
import com.example.jobportal.model.User;
import com.example.jobportal.repository.RoleRepository;
import com.example.jobportal.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository,RoleRepository roleRepository){

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User registerUser(UserRequestDTO userRequestDTO) {
        if(userRepository.findByEmail(userRequestDTO.getEmail()).isPresent()){
            throw new EmailAlreadyExistsException("Email already registered");
        }

        User user = new User();
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(userRequestDTO.getPassword());
        Set<Role> roles = new HashSet<>();

        if(userRequestDTO.getRole() == null){
            Role defaultRole = roleRepository.findByName(ERole.ROLE_APPLICANT)
                    .orElseThrow(() -> new RuntimeException("Error: Default role not found"));
            roles.add(defaultRole);
            user.setRoles(roles);
        }else {
            for (ERole roleStr : userRequestDTO.getRole()) {
                String eRole = String.valueOf(roleStr); // Convert string to enum
                Role role = roleRepository.findByName(ERole.valueOf(eRole))
                        .orElseThrow(() -> new RuntimeException("Error: Role not found"));
                roles.add(role);
                user.setRoles(roles);
            }
        }

        return userRepository.save(user);
    }
}
