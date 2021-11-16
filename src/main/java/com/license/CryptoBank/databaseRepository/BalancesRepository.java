package com.license.CryptoBank.databaseRepository;

import com.license.CryptoBank.databaseEntities.Balances;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalancesRepository extends JpaRepository<Balances, Long> {
    Balances findByUsername(String username);
}

