package com.license.CryptoBank.Service.Address;

import com.license.CryptoBank.Database.Entities.ETHAddress;

import java.util.List;

public interface AddressService {
    ETHAddress saveAdress(ETHAddress ethAddress);

    ETHAddress getEthAdressById(Long id);

    List<ETHAddress> getAdresses();
}
