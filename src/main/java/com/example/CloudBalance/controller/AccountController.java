package com.example.CloudBalance.controller;

import com.example.CloudBalance.DTO.AccountDTO;
import com.example.CloudBalance.service.account.AccountServiceImpl;
import jakarta.servlet.http.HttpServlet;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountServiceImpl accountService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_READ_ONLY', 'ROLE_CUSTOMER')")
    @GetMapping()
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        List<AccountDTO> accountDTO = accountService.getAllAccounts();
        return ResponseEntity.ok(accountDTO);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<AccountDTO> createAccount(@Valid @RequestBody AccountDTO accountDTO) {
        AccountDTO createdAccount = accountService.createAccount(accountDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }
}
