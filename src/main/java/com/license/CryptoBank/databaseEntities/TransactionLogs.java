package com.license.CryptoBank.databaseEntities;

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
public class TransactionLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    public String getUsername() {

        return username;
    }

    //  @ManyToMany(fetch = FetchType.EAGER)
    //  private Collection<String> ETH_Logs =  new ArrayList<>();

    // @ManyToMany(fetch = FetchType.EAGER)
    //  private Collection<String> FIAT_Logs =  new ArrayList<>();
}
