package com.example.CloudBalance.mapper;

import com.example.CloudBalance.DTO.UserDTO;
import com.example.CloudBalance.model.ERole;
import com.example.CloudBalance.model.User;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {


    public User userMap(UserDTO dto){
        User entity = new User();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setRole(ERole.valueOf(dto.getRole()));
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        return entity;
    }

    public UserDTO userDTOMap(User entity){
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setRole(String.valueOf(entity.getRole()));
        dto.setName(entity.getName());
        dto.setPassword(entity.getPassword());
        return dto;
    }
}
