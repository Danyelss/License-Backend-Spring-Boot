package com.license.CryptoBank.databaseService.TransactionLogsService;

import com.license.CryptoBank.databaseEntities.TransactionLogs;
import com.license.CryptoBank.databaseRepository.TransactionLogsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TransactionLogsImplementation implements TransactionLogsService {
    private final TransactionLogsRepository transactionLogsRepository;

    @Override
    public TransactionLogs saveTransactionLog(TransactionLogs transactionLogs) {
        log.info("Saving new transaction for user {} to database.", transactionLogs.getUsername());
        return transactionLogsRepository.save(transactionLogs);
    }

    @Override
    public TransactionLogs getTransactionLogsByUsername(String username) {
        log.info("Fetching TransactionLogs for user {}", username);
        return transactionLogsRepository.findByUsername(username);
    }

    @Override
    public List<TransactionLogs> getTransactionLogs() {
        log.info("Fething all TransactionLogs");
        return transactionLogsRepository.findAll();
    }
}
