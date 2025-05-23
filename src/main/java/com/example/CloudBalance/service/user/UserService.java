package com.example.CloudBalance.service.user;

import com.example.CloudBalance.DTO.UserDTO;

import java.util.List;

public interface UserService{
    public UserDTO createUser(UserDTO userDTO);
    public UserDTO getUser(Long id);
    public String updateUser(Long id, UserDTO userDTO);
    public List<UserDTO> getAllUsers();

    UserDTO getUserByEmail(String email);

    public UserDTO getCurrentUser();
}
