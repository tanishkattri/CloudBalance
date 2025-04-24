package com.example.CloudBalance.controller;

import com.example.CloudBalance.DTO.ApiResponse;
import com.example.CloudBalance.DTO.AuthResponse;
import com.example.CloudBalance.DTO.LoginRequest;
import com.example.CloudBalance.repository.BlackListTokenRepository;
import com.example.CloudBalance.repository.UserRepository;
import com.example.CloudBalance.security.jwt.JWTService;
import com.example.CloudBalance.service.auth.AuthService;
import com.example.CloudBalance.utils.ResponseBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JWTService jwtService;
    private final BlackListTokenRepository blackListTokenRepository;
    private final UserRepository userRepository;


//    @PostMapping("signup")
//    public ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody RegisterReq registerReq) {
//        return ResponseEntity.ok(authService.register(registerReq));
//    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<AuthResponse>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = authService.authenticateUser(loginRequest);
        return ResponseEntity.status(200).body(new ApiResponse<>(HttpStatus.OK.value(), "Login successful", authResponse));
    }
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Integer>> logout(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            authService.logoutUser(token);
            return ResponseEntity.status(200).body(
                    new ApiResponse<>(HttpStatus.OK.value(), "Logout successful", null));
        } else {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Invalid token", null));
        }
    }

}
