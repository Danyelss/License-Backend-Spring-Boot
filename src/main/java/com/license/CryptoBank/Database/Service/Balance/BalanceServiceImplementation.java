package com.license.CryptoBank.Database.Service.Balance;

import com.license.CryptoBank.Database.Entities.Balance;
import com.license.CryptoBank.Database.Repository.BalanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BalanceServiceImplementation implements BalanceService {
    private final BalanceRepository balanceRepository;

    public void loadBalanceByUsername(String username) {
        Balance balance = balanceRepository.findByUsername(username);
        if (balance == null) {
            log.error("Balance not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", username);
        }
    }

    @Override
    public Balance saveBalance(Balance balance) {
        log.info("Saving new balance for user {} to database.", balance.getUsername());
        return balanceRepository.save(balance);
    }

    @Override
    public Balance getBalanceByUsername(String username) {
        log.info("Fetching balance for user {}", username);
        return balanceRepository.findByUsername(username);
    }

    @Override
    public List<Balance> getBalance() {
        log.info("Fething all balances");
        return balanceRepository.findAll();
    }

    @Override
    public void updateBalanceById(long id, BigDecimal value) {
        log.info("Update balance for id {}", id);

        Balance balanceFromDb = balanceRepository.getById(id);
        balanceFromDb.setETH_BAL(value);

        balanceRepository.save(balanceFromDb);
    }

    @Override
    public void saveETH_TransactionLogsToUsername(String username, String log) {
        balanceRepository.findByUsername(username).addETH_TransactionLog(log);
    }

    @Override
    public void saveFIAT_TransactionLogsToUsername(String username, String log) {
        balanceRepository.findByUsername(username).addFIAT_TransactionLog(log);
    }

}
