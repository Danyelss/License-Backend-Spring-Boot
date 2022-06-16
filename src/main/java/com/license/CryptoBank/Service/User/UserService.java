package com.license.CryptoBank.Service.User;

import com.license.CryptoBank.Database.Entities.Role;
import com.license.CryptoBank.Database.Entities.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);

    void registerUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String username, String roleName);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    User getUserById(String id);

    List<User> getUsers();
}

