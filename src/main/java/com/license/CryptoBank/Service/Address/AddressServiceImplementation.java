package com.license.CryptoBank.Service.Address;

import com.license.CryptoBank.Database.Entities.ETHAddress;
import com.license.CryptoBank.Database.Repository.ETHAddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AddressServiceImplementation implements AddressService {
    private final ETHAddressRepository ethAddressRepository;

    @Override
    public ETHAddress saveAdress(ETHAddress ethAddress) {
        log.info("Saving new adress with id {} to database.", ethAddress.getId());
        return ethAddressRepository.save(ethAddress);
    }

    @Override
    public ETHAddress getEthAdressById(Long id) {
        log.info("Fetching adress with id {}", id);
        return ethAddressRepository.getById(id);
    }

    @Override
    public List<ETHAddress> getAdresses() {
        log.info("Fething all adresses");
        return ethAddressRepository.findAll();
    }

}

