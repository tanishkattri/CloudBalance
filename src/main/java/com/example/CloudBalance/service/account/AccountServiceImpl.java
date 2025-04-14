package com.example.CloudBalance.service.account;

import com.example.CloudBalance.DTO.AccountDTO;
import com.example.CloudBalance.mapper.AccountMapper;
import com.example.CloudBalance.model.Account;
import com.example.CloudBalance.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService{
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountMapper accountMapper;

    public AccountDTO createAccount(AccountDTO accountDTO) {
        Account account = accountMapper.map(accountDTO);
        accountRepository.save(account);
        accountDTO.setId(account.getId());
        return accountDTO;
    }
    public AccountDTO getAccountByAccountNumber(Long accountNumber){
        Account account = accountRepository.findByAccountNumber(accountNumber);
        return accountMapper.map(account);
    }

    public List<AccountDTO> getAllAccounts(){
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map(accountMapper::map).collect(Collectors.toList());
    }
}
