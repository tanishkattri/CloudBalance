package com.example.CloudBalance.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
public class LoginRequest {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
