package com.example.jobportal.model;

import com.example.jobportal.Role.ERole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length=20, unique = true)
    private ERole name;

    public Role(ERole name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{id=" + id + ", name=" + name + "}";
    }

}
