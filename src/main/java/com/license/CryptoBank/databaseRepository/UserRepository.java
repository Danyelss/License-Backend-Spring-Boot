package com.license.CryptoBank.databaseRepository;

import com.license.CryptoBank.databaseEntities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findById(String id);
}
