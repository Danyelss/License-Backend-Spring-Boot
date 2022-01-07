package com.license.CryptoBank.Database.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min=5, message = "The username must have at least 5 characters")
    private String username;

    @NotNull
    @Size(min=6, message = "The password must have at least 6 characters")
    private String password;

    @NotNull
    @Size(min=1, message = "The First Name must not be null")
    private String first_name;

    @NotNull
    @Size(min=1, message = "The Last Name must not be null")
    private String last_name;

    @NotNull
    @Size(min=1, message = "The email must not be null")
    private String email;

    @NotNull
    @Size(min=1, message = "The Phone Number must not be null")
    private String phone_number;

    public String getUsername() {

        return username;
    }

    public Long getId() {

        return id;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();
}

