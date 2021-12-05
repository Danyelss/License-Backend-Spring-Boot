package com.license.CryptoBank.Database.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Balances {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String username;

    public String getUsername() {

        return username;
    }

    @NotNull
    private double ETH_BAL;
    @NotNull
    private double FIAT_BAL;

    //@Type(type = "string")
    @ElementCollection
    private List<String> ETH_TransactionLogs = new ArrayList<>();

    //@Type(type = "string")
    @ElementCollection
    private List<String> FIAT_TransactionLogs = new ArrayList<>();

    public void addETH_TransactionLog(String string) {
        ETH_TransactionLogs.add(string);
    }

    public void addFIAT_TransactionLog(String string) {
        FIAT_TransactionLogs.add(string);
    }
}
