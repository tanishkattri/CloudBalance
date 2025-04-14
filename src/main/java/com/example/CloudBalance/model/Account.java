package com.example.CloudBalance.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long accountNumber;

    @Column(nullable = false)
    private String accountName;

    @Column(unique = true, nullable = false)
    private String arnNumber;


}
