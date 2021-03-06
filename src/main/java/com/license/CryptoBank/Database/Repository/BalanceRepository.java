package com.license.CryptoBank.Database.Repository;

import com.license.CryptoBank.Database.Entities.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {
    Balance findByUsername(String username);
    Balance getById(long id);
}

