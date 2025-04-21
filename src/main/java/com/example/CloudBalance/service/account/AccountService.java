package com.example.CloudBalance.service.account;

import com.example.CloudBalance.DTO.AccountDTO;

import java.util.List;

public interface AccountService {
    public AccountDTO createAccount(AccountDTO accountDTO);
    public AccountDTO getAccountByAccountNumber(Long accountNumber);
    public List<AccountDTO> getAllAccounts();

}
