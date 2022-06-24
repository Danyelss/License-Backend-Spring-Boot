package com.license.CryptoBank.Database.Repository;

import com.license.CryptoBank.Database.Entities.ETHAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ETHAddressRepository extends JpaRepository<ETHAddress, Long> {
    ETHAddress getById(Long id);
}