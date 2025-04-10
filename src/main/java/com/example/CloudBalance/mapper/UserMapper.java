package com.example.CloudBalance.mapper;

import com.example.CloudBalance.DTO.UserDTO;
import com.example.CloudBalance.model.ERole;
import com.example.CloudBalance.model.User;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {


    public User userMap(UserDTO dto){
        User entity = new User();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
//        entity.setLastLogin(dto.getLastLogin());
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
        dto.setFirstName(entity.getFirstName());
        dto.setLastLogin(entity.getLastLogin());
        dto.setLastLogin(entity.getLastLogin());
        dto.setPassword(entity.getPassword());
        return dto;
    }
    public UserDTO userDTOMapWithoutPassword(User entity) {
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setRole(String.valueOf(entity.getRole()));
        dto.setLastLogin(entity.getLastLogin());

        return dto;
    }
}
