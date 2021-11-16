package com.license.CryptoBank.databaseEntities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Balances {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    public String getUsername() {

        return username;
    }

    private double ETH_BAL;
    private double FIAT_BAL;

    //@ManyToOne(fetch = FetchType.EAGER)
   // private TransactionLogs logs;
}
