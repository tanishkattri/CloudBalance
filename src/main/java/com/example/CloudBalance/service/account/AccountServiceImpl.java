package com.example.CloudBalance.service.account;

import com.example.CloudBalance.DTO.AccountDTO;
import com.example.CloudBalance.utils.mapper.AccountMapper;
import com.example.CloudBalance.model.Account;
import com.example.CloudBalance.model.User;
import com.example.CloudBalance.repository.AccountRepository;
import com.example.CloudBalance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService{
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) {
        Account account = accountMapper.map(accountDTO);
        accountRepository.save(account);
        accountDTO.setId(account.getId());
        return accountDTO;
    }

    @Override
    public AccountDTO getAccountByAccountNumber(Long accountNumber){
        Account account = accountRepository.findByAccountNumber(accountNumber);
        return accountMapper.map(account);
    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isCustomer = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_CUSTOMER"));

        if (isCustomer) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

            List<Account> accounts = user.getAccount();  // Assuming getter is getAccount()
            return accounts.stream()
                    .map(accountMapper::map)
                    .collect(Collectors.toList());
        } else {
            return accountRepository.findAll()
                    .stream()
                    .map(accountMapper::map)
                    .collect(Collectors.toList());
        }
    }




}
