package com.example.CloudBalance.service.auth;

import com.example.CloudBalance.DTO.AuthResponse;
import com.example.CloudBalance.DTO.LoginRequest;
import com.example.CloudBalance.utils.mapper.UserMapper;
import com.example.CloudBalance.model.BlackListToken;
import com.example.CloudBalance.repository.BlackListTokenRepository;
import com.example.CloudBalance.repository.UserRepository;
import com.example.CloudBalance.security.jwt.JWTService;
import com.example.CloudBalance.security.userDetail.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper UserMapper;
    private final BlackListTokenRepository blackListTokenRepository;

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
//        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var jwt = jwtService.generateToken((UserDetails) new UserDetailsImpl(user));
        return AuthResponse.builder()
                .token(jwt)
                .build();
    }


    public void logoutUser(String token) {
        Date expiration = jwtService.getExpirationDateFromToken(token);

        // Save token to blacklist
        BlackListToken blackListToken = new BlackListToken();
        blackListToken.setToken(token);
        blackListToken.setExpirationDate(expiration);
        blackListTokenRepository.save(blackListToken);

        // Update user's last login
        String email = jwtService.extractUsername(token); // `sub` claim
        userRepository.findByEmail(email).ifPresent(user -> {
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);
        });

        // Clear security context
        SecurityContextHolder.clearContext();
    }
}