package com.license.CryptoBank.databaseService.TransactionLogsService;

import com.license.CryptoBank.databaseEntities.TransactionLogs;

import java.util.List;

public interface TransactionLogsService {
    TransactionLogs saveTransactionLog(TransactionLogs transactionLogs);

    TransactionLogs getTransactionLogsByUsername(String username);

    List<TransactionLogs> getTransactionLogs();
}
