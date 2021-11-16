package com.license.CryptoBank.databaseRepository;

import com.license.CryptoBank.databaseEntities.ETHAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ETHAddressRepository extends JpaRepository<ETHAddress, Long> {
    ETHAddress findByUsername(String username);
}