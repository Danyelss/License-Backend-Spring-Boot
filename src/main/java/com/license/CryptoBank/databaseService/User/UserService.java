package com.license.CryptoBank.databaseService.User;

import com.license.CryptoBank.databaseEntities.Role;
import com.license.CryptoBank.databaseEntities.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String username, String roleName);

    User getUserByUsername(String username);

    User getUserById(String id);

    List<User> getUsers();
}

