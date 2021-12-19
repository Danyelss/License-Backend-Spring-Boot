package com.license.CryptoBank.Database.Service.Transaction;

import com.license.CryptoBank.Database.Entities.ETHAddress;

import java.util.List;

public interface TransactionService {
    ETHAddress saveAdress(ETHAddress ethAddress);

    ETHAddress getEthAdressById(Long id);

    List<ETHAddress> getAdresses();
}
