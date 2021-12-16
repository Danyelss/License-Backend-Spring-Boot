package com.license.CryptoBank.Database.Service.Transaction;

import com.license.CryptoBank.Database.Entities.Balances;
import com.license.CryptoBank.Database.Entities.ETHAddress;
import com.license.CryptoBank.Database.Repository.BalancesRepository;
import com.license.CryptoBank.Database.Repository.ETHAddressRepository;
import com.license.CryptoBank.Database.Service.Balances.BalancesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


import com.license.CryptoBank.Database.Entities.Balances;
        import com.license.CryptoBank.Database.Repository.BalancesRepository;
        import lombok.RequiredArgsConstructor;
        import lombok.extern.slf4j.Slf4j;
        import org.springframework.security.core.userdetails.UsernameNotFoundException;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Transactional;

        import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TransactionServiceImplementation implements TransactionService {
    private final BalancesRepository balancesRepository;
    private final ETHAddressRepository ethAddressRepository;

    @Override
    public ETHAddress saveAdress(ETHAddress ethAddress) {
        log.info("Saving new adress with id {} to database.", ethAddress.getId());
        return ethAddressRepository.save(ethAddress);
    }

    @Override
    public double getBalanceFromAdressById(Long id) {
        ETHAddress ethAddress = ethAddressRepository.getById(id);
        double balance = ethAddress.getBalance();

        log.info("Adress with id {} has {} eth.", id, balance);

        return balance;
    }

    @Override
    public ETHAddress getEthAdressById(Long id) {
        log.info("Fetching adress with id {}", id);
        return ethAddressRepository.getById(id);
    }

    @Override
    public List<ETHAddress> getAdresses() {
        log.info("Fething all adresses");
        return ethAddressRepository.findAll();
    }

}

