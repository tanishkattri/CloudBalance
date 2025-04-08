package com.example.CloudBalance.controller;

import com.example.CloudBalance.DTO.AuthResponse;
import com.example.CloudBalance.DTO.LoginRequest;
import com.example.CloudBalance.model.BlackListToken;
import com.example.CloudBalance.repository.BlackListTokenRepository;
import com.example.CloudBalance.security.jwt.JWTService;
import com.example.CloudBalance.service.AuthService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private JWTService jwtService;
    private final BlackListTokenRepository blackListTokenRepository;


//    @PostMapping("signup")
//    public ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody RegisterReq registerReq) {
//        return ResponseEntity.ok(authService.register(registerReq));
//    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticateUser(loginRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            Date expiration = jwtService.getExpirationDateFromToken(token);
            BlackListToken blackListToken = new BlackListToken();
            blackListToken.setToken(token);
            blackListToken.setExpirationDate(expiration);
            blackListTokenRepository.save(blackListToken);
            SecurityContextHolder.clearContext();
            return ResponseEntity.ok("Logout successful");
        }else {
            return ResponseEntity.badRequest().body("Invalid token");
        }
    }

}
