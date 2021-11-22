package com.license.CryptoBank;

import com.license.CryptoBank.Database.Entities.Balances;
import com.license.CryptoBank.Database.Entities.Role;
import com.license.CryptoBank.Database.Entities.User;
import com.license.CryptoBank.Database.Service.Balances.BalancesService;
import com.license.CryptoBank.Database.Service.User.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CryptoBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptoBankApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}


