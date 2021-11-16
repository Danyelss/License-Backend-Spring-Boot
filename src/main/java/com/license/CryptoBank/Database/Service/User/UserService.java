package com.license.CryptoBank.Database.Service.User;

import com.license.CryptoBank.Database.Entities.Role;
import com.license.CryptoBank.Database.Entities.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String username, String roleName);

    User getUserByUsername(String username);

    User getUserById(String id);

    List<User> getUsers();
}

