package com.license.CryptoBank;

import com.license.CryptoBank.databaseEntities.Role;
import com.license.CryptoBank.databaseEntities.User;
import com.license.CryptoBank.databaseService.User.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class CryptoBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptoBankApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_MANAGER"));
            userService.saveRole(new Role(null, "ROLE_ADMIN"));
            userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

            userService.saveUser(new User(null, "cosmin", "0000", "Pop", "Cosmin", "comsin@encode.com", "07232322", new ArrayList<>()));
            userService.saveUser(new User(null, "beni", "0000", "Beniamin", "Cigher", "beni@encode.com", "07424222", new ArrayList<>()));
            userService.saveUser(new User(null, "paula", "2002", "Paula", "Nelli", "pimonca@yahoo.com", "074342322", new ArrayList<>()));
            userService.saveUser(new User(null, "andrei", "0000", "Bretea", "Andrei", "andrei@armata.com", "0725435432", new ArrayList<>()));

            userService.addRoleToUser("cosmin", "ROLE_USER");
            userService.addRoleToUser("beni", "ROLE_USER");
            userService.addRoleToUser("paula", "ROLE_USER");
            userService.addRoleToUser("andrei", "ROLE_USER");
        };
    }


}


