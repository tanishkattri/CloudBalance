package com.example.CloudBalance.repository;

import com.example.CloudBalance.model.BlackListToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListTokenRepository extends JpaRepository<BlackListToken, Long> {
    BlackListToken findByToken(String token);
    void deleteByToken(String token);

    boolean existsByToken(String jwt);
}
