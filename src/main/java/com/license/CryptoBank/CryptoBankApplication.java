package com.license.CryptoBank;

import com.license.CryptoBank.Database.Entities.Balance;
import com.license.CryptoBank.Database.Entities.ETHAddress;
import com.license.CryptoBank.Database.Entities.Role;
import com.license.CryptoBank.Database.Entities.User;
import com.license.CryptoBank.Database.Service.Balance.BalanceService;
import com.license.CryptoBank.Database.Service.Encryption.EncryptionDecryption;
import com.license.CryptoBank.Database.Service.Transaction.TransactionService;
import com.license.CryptoBank.Database.Service.User.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class CryptoBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptoBankApplication.class, args);
    }

    //decrypt and encrypt alg

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    CommandLineRunner run(UserService userService, BalanceService balancesService, TransactionService transactionService) {
        return args -> {
            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_MANAGER"));
            userService.saveRole(new Role(null, "ROLE_ADMIN"));
            userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

            userService.saveUser(new User(null, "cosmin", "0000", "Pop", "Cosmin", "comsin@encode.com", "07232322", new ArrayList<>()));
            userService.saveUser(new User(null, "beni1", "0000", "Beniamin", "Cigher", "beni@encode.com", "07424222", new ArrayList<>()));
            userService.saveUser(new User(null, "paula", "0000", "Paula", "Nelli", "pimonca@yahoo.com", "074342322", new ArrayList<>()));
            userService.saveUser(new User(null, "andrei", "0000", "Bretea", "Andrei", "andrei@armata.com", "0725435432", new ArrayList<>()));
            userService.saveUser(new User(null, "an12345", "00", "g", "A", "andr", "07254", new ArrayList<>()));

            userService.addRoleToUser("cosmin", "ROLE_USER");
            userService.addRoleToUser("beni1", "ROLE_ADMIN");
            userService.addRoleToUser("paula", "ROLE_USER");
            userService.addRoleToUser("andrei", "ROLE_USER");
            userService.addRoleToUser("an12345", "ROLE_USER");

            List<String> ETHTestLog = new ArrayList<>();
            ETHTestLog.add("this");
            ETHTestLog.add("is");
            ETHTestLog.add("a");
            ETHTestLog.add("test");
            ETHTestLog.add("log");

            List<String> FIATTestLog = new ArrayList<>();
            FIATTestLog.add("FIAT");
            FIATTestLog.add("this");
            FIATTestLog.add("is");
            FIATTestLog.add("a");
            FIATTestLog.add("test");
            FIATTestLog.add("log");
            FIATTestLog.add("on");
            FIATTestLog.add("FIAT");

            balancesService.saveBalance(new Balance(null, "cosmin", BigDecimal.ZERO, 0, ETHTestLog, FIATTestLog));
            balancesService.saveBalance(new Balance(null, "beni", BigDecimal.ONE, 1, ETHTestLog, FIATTestLog));
            balancesService.saveBalance(new Balance(null, "paula", BigDecimal.valueOf(2.5), 2.3, ETHTestLog, FIATTestLog));
            balancesService.saveBalance(new Balance(null, "andrei", BigDecimal.valueOf(132), 64, ETHTestLog, FIATTestLog));
            balancesService.saveBalance(new Balance(null, "an12345", BigDecimal.valueOf(132), 64, ETHTestLog, FIATTestLog));

            IvParameterSpec parameterSpec = EncryptionDecryption.getParameterSpec();
            SecretKey secretKey = EncryptionDecryption.getSecretKey();

            transactionService.saveAdress(new ETHAddress(null, "0x031aCa498E1e4De96E54F22A35fc300a67D4acF2", EncryptionDecryption.encrypt("AES/CBC/PKCS5Padding", "9af469b16a237c32d6825b8f96be6b7abdbccefc49ee401e680cd521ff45f0e8", secretKey, parameterSpec)));
            transactionService.saveAdress(new ETHAddress(null, "0x147d71A3C682280D5C06A79d69ecd82190f253FE", EncryptionDecryption.encrypt("AES/CBC/PKCS5Padding", "6152b2b4f4139006c08527946d00fe73ed2ff67ff9d3e1f6a117c8063c8124f3", secretKey, parameterSpec)));
            transactionService.saveAdress(new ETHAddress(null, "0xc57d751C013C8087e0FD71296f76cdBC31cDe388", EncryptionDecryption.encrypt("AES/CBC/PKCS5Padding", "87bd3a15f55db8751671238f8b61a656cf30620ef6bd75dcd747422a0ffc3067", secretKey, parameterSpec)));
            transactionService.saveAdress(new ETHAddress(null, "0x3090a636a8F6ed016dD60E6026A3992FdD57BD18", EncryptionDecryption.encrypt("AES/CBC/PKCS5Padding", "071f1809ecbbf0e2d6907e82a7f6e891659dbc51e52acab8abcc23a9f72ce39e", secretKey, parameterSpec)));
            transactionService.saveAdress(new ETHAddress(null, "0x52DBEd018e7fC3edF3Ed922B4D111f79AD81A091", EncryptionDecryption.encrypt("AES/CBC/PKCS5Padding", "0742660d4176e39c892669e4e808ef4e6ff95706774790686ba1098f366cac71", secretKey, parameterSpec)));
            transactionService.saveAdress(new ETHAddress(null, "0x441ED9b22eDc0ca5a6Fd3260D5d4f6FEA9fab60c", EncryptionDecryption.encrypt("AES/CBC/PKCS5Padding", "18af97f68735354d97454e985bd7246f59a222073f5925c26408cb301cfc4ccc", secretKey, parameterSpec)));

        };
    }
}


