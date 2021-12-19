package com.license.CryptoBank.Database.Service.Balance;

import com.license.CryptoBank.Database.Entities.Balance;

import java.math.BigDecimal;
import java.util.List;

public interface BalanceService {
    Balance saveBalance(Balance balance);

    Balance getBalanceByUsername(String username);

    List<Balance> getBalance();

    void updateBalanceById(long id, BigDecimal value);

    void saveETH_TransactionLogsToUsername(String username, String log);

    void saveFIAT_TransactionLogsToUsername(String username, String log);

}
