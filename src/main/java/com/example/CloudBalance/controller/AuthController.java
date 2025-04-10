package com.example.CloudBalance.controller;

import com.example.CloudBalance.DTO.AuthResponse;
import com.example.CloudBalance.DTO.LoginRequest;
import com.example.CloudBalance.model.BlackListToken;
import com.example.CloudBalance.model.User;
import com.example.CloudBalance.repository.BlackListTokenRepository;
import com.example.CloudBalance.repository.UserRepository;
import com.example.CloudBalance.security.jwt.JWTService;
import com.example.CloudBalance.service.AuthService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

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
    public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticateUser(loginRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            log.info(token);
            Date expiration = jwtService.getExpirationDateFromToken(token);
            BlackListToken blackListToken = new BlackListToken();
            blackListToken.setToken(token);
            blackListToken.setExpirationDate(expiration);
            blackListTokenRepository.save(blackListToken);

            String email = jwtService.extractUsername(token); // `sub` claim
            Optional<User> userOpt = userRepository.findByEmail(email);
            userOpt.ifPresent(user -> {
                user.setLastLogin(LocalDateTime.now());
                userRepository.save(user);
            });
            SecurityContextHolder.clearContext();
            return ResponseEntity.ok("Logout successful");
        }else {
            return ResponseEntity.badRequest().body("Invalid token");
        }
    }

}
