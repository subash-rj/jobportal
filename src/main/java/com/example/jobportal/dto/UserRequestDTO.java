package com.example.jobportal.dto;

import com.example.jobportal.Role.ERole;
import com.example.jobportal.model.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class UserRequestDTO {

    @NotBlank(message="Name is required")
    private String name;

    @NotBlank(message="Email is required")
    @Email(message="Invalid Email format")
    private String email;

    @NotBlank(message="Password is required")
    @Size(min=6, message="Password must be at least 6 characters")
    private String password;

    @Enumerated(EnumType.STRING)
    private Set<ERole> role;

    public UserRequestDTO(String name, String email, String password, Set<ERole> role) {
        this.name = name;
        this.email=email;
        this.password = password;
        this.role=role;
    }

    public UserRequestDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<ERole> getRole(){
        return role;
    }

    public void setRole(Set<ERole> role){
        this.role = role;
    }


}
