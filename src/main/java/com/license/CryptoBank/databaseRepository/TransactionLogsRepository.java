package com.license.CryptoBank.databaseRepository;

import com.license.CryptoBank.databaseEntities.TransactionLogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionLogsRepository extends JpaRepository<TransactionLogs, Long> {
    TransactionLogs findByUsername(String username);
}
