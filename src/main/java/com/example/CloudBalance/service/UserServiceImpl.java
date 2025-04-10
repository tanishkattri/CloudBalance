package com.example.CloudBalance.service;

import com.example.CloudBalance.DTO.UserDTO;
import com.example.CloudBalance.mapper.UserMapper;
import com.example.CloudBalance.model.ERole;
import com.example.CloudBalance.model.User;
import com.example.CloudBalance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.userMap(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println("Password"+user.getPassword());// Hash the password
        userRepository.save(user);
        userDTO.setId(user.getId());
        return userDTO;
    }

    @Override
    public UserDTO getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
//        user.setPassword(null);
        return userMapper.userDTOMapWithoutPassword(user);
    }

//    @Transactional
//    @Override
//    public String updateUser(Long id, UserDTO userDTO) {
//        User existingUser = userRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
//        existingUser.setEmail(userDTO.getEmail());
//        existingUser.setName(userDTO.getName());
//        if (userDTO.getPassword() != null) {
//            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//        }
//        existingUser.setRole(ERole.valueOf(userDTO.getRole()));
//        User updatedUser = userRepository.save(existingUser);
//        return "User Updated";
//    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper:: userDTOMapWithoutPassword).collect(Collectors.toList());
    }

    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return userMapper.userDTOMapWithoutPassword(user);
    }

    public UserDTO getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userRepository.findByEmail(email).orElseThrow();
        return userMapper.userDTOMapWithoutPassword(user);
    }
}
