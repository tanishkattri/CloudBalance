package com.example.CloudBalance.mapper;

import com.example.CloudBalance.DTO.UserDTO;
import com.example.CloudBalance.model.Account;
import com.example.CloudBalance.model.ERole;
import com.example.CloudBalance.model.User;
import com.example.CloudBalance.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class UserMapper {
    @Autowired
    private AccountRepository accountRepository;


    public User userMap(UserDTO dto){
        User entity = new User();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setRole(ERole.valueOf(dto.getRole()));
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());

        if (dto.getAccounts() != null && !dto.getAccounts().isEmpty()) {
            List<Account> accounts = dto.getAccounts().stream().map(id -> {
                return accountRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Account not found with id: " + id)
                        );
            }).collect(Collectors.toList());

            entity.setAccount(accounts);
        } else {
            entity.setAccount(Collections.emptyList());
        }
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
        dto.setAccounts(entity.getAccount().stream().map(Account::getId).collect(Collectors.toList()));
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
        dto.setAccounts(entity.getAccount().stream().map(Account::getId).collect(Collectors.toList()));
        return dto;
    }
}
