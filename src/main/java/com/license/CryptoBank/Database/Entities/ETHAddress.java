package com.license.CryptoBank.Database.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ETHAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {

        return id;
    }

    private String address;

    private String privateKey; // have to encrypt it

    private double balance;

    public String getAddress() {
        return address;
    }

    public String getPrivateKey() {
        return privateKey;
    } // have to encrypt it

    public double getBalance() {
        return balance;
    }
}
