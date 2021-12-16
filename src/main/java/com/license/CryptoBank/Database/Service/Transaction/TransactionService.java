package com.license.CryptoBank.Database.Service.Transaction;

import com.license.CryptoBank.Database.Entities.Balances;
import com.license.CryptoBank.Database.Entities.ETHAddress;

import java.util.List;

public interface TransactionService {
    ETHAddress saveAdress(ETHAddress ethAddress);

    double getBalanceFromAdressById(Long id);

    ETHAddress getEthAdressById(Long id);

    List<ETHAddress> getAdresses();
}
