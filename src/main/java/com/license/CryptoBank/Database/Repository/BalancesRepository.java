package com.license.CryptoBank.Database.Repository;

import com.license.CryptoBank.Database.Entities.Balances;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalancesRepository extends JpaRepository<Balances, Long> {
    Balances findByUsername(String username);
}

