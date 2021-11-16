package com.license.CryptoBank.Database.Repository;

import com.license.CryptoBank.Database.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
