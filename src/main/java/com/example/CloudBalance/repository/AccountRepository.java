package com.example.CloudBalance.repository;

import com.example.CloudBalance.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByAccountNumber(Long accountNumber);
    boolean existsByAccountNumber(Long accountNumber);
}
