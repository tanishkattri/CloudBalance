package com.example.CloudBalance.controller;

import com.example.CloudBalance.DTO.ApiResponse;
import com.example.CloudBalance.DTO.UserDTO;
import com.example.CloudBalance.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // CREATE user
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<UserDTO>> createUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED) // 201 Created
                .body(new ApiResponse<>(201, "User created successfully", createdUser));    }

    // GET single user by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_READ_ONLY', 'ROLE_CUSTOMER')")
    public ResponseEntity<ApiResponse<UserDTO>> getUser(@PathVariable Long id) {
        UserDTO userDTO = userService.getUser(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "User fetched successfully", userDTO));
    }

    // GET all users
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_READ_ONLY')")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(new ApiResponse<>(200, "Users fetched successfully", users));
    }

    // GET current user
    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_READ_ONLY', 'ROLE_CUSTOMER')")
    public ResponseEntity<ApiResponse<UserDTO>> getCurrentUser() {
        UserDTO dto = userService.getCurrentUser();
        return ResponseEntity.ok(new ApiResponse<>(200, "Current user fetched successfully", dto));
    }

    // UPDATE user by ID
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<String>> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        String updatedMessage = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(new ApiResponse<>(200, "User updated successfully", updatedMessage));
    }
}
