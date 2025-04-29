package com.example.CloudBalance.service.user;

import com.example.CloudBalance.DTO.UserDTO;
import com.example.CloudBalance.exception.AccountNotFoundException;
import com.example.CloudBalance.exception.EmailAlreadyExistsException;
import com.example.CloudBalance.exception.UserNotFoundException;
import com.example.CloudBalance.utils.mapper.UserMapper;
import com.example.CloudBalance.model.Account;
import com.example.CloudBalance.model.ERole;
import com.example.CloudBalance.model.User;
import com.example.CloudBalance.repository.AccountRepository;
import com.example.CloudBalance.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.userMap(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println("Password"+user.getPassword());// Hash the password
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new EmailAlreadyExistsException("A user with this email already exists" );
        }
        userRepository.save(user);
        userDTO.setId(user.getId());
        return userDTO;
    }

    @Override
    public UserDTO getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No user found with id: " + id));
        return userMapper.userDTOMapWithoutPassword(user);
    }



    @Transactional
    @Override
    public String updateUser(Long id, @Valid UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No user found with id: " + id));

        existingUser.setEmail(userDTO.getEmail());
        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setLastName(userDTO.getLastName());

        ERole newRole = ERole.valueOf(userDTO.getRole());
        existingUser.setRole(newRole);

        if (userDTO.getPassword() != null && !userDTO.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        if (newRole == ERole.CUSTOMER) {
            List<Account> updatedAccounts = userDTO.getAccounts() != null
                    ? userDTO.getAccounts().stream()
                    .map(idVal -> accountRepository.findById(idVal)
                            .orElseThrow(() -> new AccountNotFoundException("No account found with id: " + idVal)))
                    .collect(Collectors.toList())
                    : new ArrayList<>();

            existingUser.getAccount().clear();
            existingUser.getAccount().addAll(updatedAccounts);
        } else {
            existingUser.getAccount().clear();
        }

        userRepository.save(existingUser);
        return "User Updated";
    }





    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper:: userDTOMapWithoutPassword).collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("No user found with email: " + email));
        return userMapper.userDTOMapWithoutPassword(user);
    }


    @Override
    public UserDTO getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Logged-in user not found with email: " + email));
        return userMapper.userDTOMapWithoutPassword(user);
    }
}
