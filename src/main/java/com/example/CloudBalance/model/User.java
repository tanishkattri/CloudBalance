package com.example.CloudBalance.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @Email
    @NotNull
    @Column(unique = true, nullable = false)
    private String email;

    @NotNull
    @Size(min = 8)
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private ERole role;

}
