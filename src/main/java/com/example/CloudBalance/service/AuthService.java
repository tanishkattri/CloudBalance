package com.example.CloudBalance.service;

import com.example.CloudBalance.DTO.AuthResponse;
import com.example.CloudBalance.DTO.LoginRequest;
import com.example.CloudBalance.repository.UserRepository;
import com.example.CloudBalance.security.jwt.JWTService;
import com.example.CloudBalance.security.userDetail.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

//    public AuthResponse register(@Valid LoginRequest loginRequest) {
//        var user = User.builder()
//                .email(loginRequest.getEmail())
//                .password(loginRequest.getPassword())
//                .build();
//        userRepository.save(user);
//        var jwt = jwtService.generateToken(user);
//        return AuthResponse.builder()
//                .token(jwt)
//                .build();
//    }


    public AuthResponse authenticateUser(@Valid LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();

        SecurityContextHolder.getContext().setAuthentication(authentication);
        var jwt = jwtService.generateToken((UserDetails) new UserDetailsImpl(user));
        return AuthResponse.builder()
                .token(jwt)
                .build();

    }
}
