package com.license.CryptoBank.Database.Repository;

import com.license.CryptoBank.Database.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findById(String id);
}
