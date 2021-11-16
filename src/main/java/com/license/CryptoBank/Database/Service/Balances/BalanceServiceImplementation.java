package com.license.CryptoBank.Database.Service.Balances;

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
public class BalanceServiceImplementation implements BalancesService {
    private final BalancesRepository balancesRepository;

    public void loadBalanceByUsername(String username) {
        Balances balances = balancesRepository.findByUsername(username);
        if (balances == null) {
            log.error("Balance not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", username);
        }
    }

    @Override
    public Balances saveBalance(Balances balances) {
        log.info("Saving new balance for user {} to database.", balances.getUsername());
        return balancesRepository.save(balances);
    }

    @Override
    public Balances getBalanceByUsername(String username) {
        log.info("Fetching balance for user {}", username);
        return balancesRepository.findByUsername(username);
    }

    @Override
    public List<Balances> getBalances() {
        log.info("Fething all balances");
        return balancesRepository.findAll();
    }

    @Override
    public void saveETH_TransactionLogsToUsername(String username, String log) {
        balancesRepository.findByUsername(username).addETH_TransactionLog(log);
    }

    @Override
    public void saveFIAT_TransactionLogsToUsername(String username, String log) {
        balancesRepository.findByUsername(username).addFIAT_TransactionLog(log);
    }

}
