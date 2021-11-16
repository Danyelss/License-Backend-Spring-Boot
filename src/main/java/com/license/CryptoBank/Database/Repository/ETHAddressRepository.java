package com.license.CryptoBank.Database.Repository;

import com.license.CryptoBank.Database.Entities.ETHAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ETHAddressRepository extends JpaRepository<ETHAddress, Long> {
    ETHAddress findByUsername(String username);
}