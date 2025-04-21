package com.example.CloudBalance.utils.mapper;

import com.example.CloudBalance.DTO.AccountDTO;
import com.example.CloudBalance.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public Account map(AccountDTO accountDTO) {
        Account account = new Account();
        account.setAccountNumber(accountDTO.getAccountNumber());
        account.setAccountName(accountDTO.getAccountName());
        account.setArnNumber(accountDTO.getArnNumber());
        return account;
    }

    public AccountDTO map(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setAccountName(account.getAccountName());
        accountDTO.setAccountNumber(account.getAccountNumber());
        accountDTO.setArnNumber(account.getArnNumber());
        return accountDTO;
    }
}
