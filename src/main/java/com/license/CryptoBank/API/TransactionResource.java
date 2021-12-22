package com.license.CryptoBank.API;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.license.CryptoBank.Database.Entities.Balance;
import com.license.CryptoBank.Database.Entities.ETHAddress;
import com.license.CryptoBank.Database.Service.Balance.BalanceService;
import com.license.CryptoBank.Database.Service.InfuraUtil.AddressUtil;
import com.license.CryptoBank.Database.Service.Transaction.TransactionService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Service
@Transactional
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TransactionResource {
    private final BalanceService balanceService;
    private final TransactionService transactionService;
    private final AddressUtil addressUtil;

    private ArrayList<ETHAddress> listOfAddresses = new ArrayList<>();       // do i need in the database? - in case of power failure
    private ArrayList<AddressData> listOfAddressesData = new ArrayList<>();
    private Map<ETHAddress, Boolean> addressMap = new HashMap<ETHAddress, Boolean>();

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        addressMap = init();
    }

    //@Scheduled - fixed delay - time -
    @Scheduled(fixedDelay = 1000 * 10, initialDelay = 1000 * 10)
    protected void periodicCheck() throws ExecutionException, InterruptedException, TimeoutException {
        ArrayList<ETHAddress> toRemoveAdress = new ArrayList<ETHAddress>();
        ArrayList<AddressData> toRemoveData = new ArrayList<AddressData>();

        if (!listOfAddresses.isEmpty()) {
            for (ETHAddress address : listOfAddresses) {

                log.info("Scanning address: {}", address.getAddress());

                int index = listOfAddresses.indexOf(address);

                log.info("With index {}", index);

                BigDecimal start = listOfAddressesData.get(index).getStartBalance();
                BigDecimal end = addressUtil.getBalance(address.getAddress()); // get actual balance

                log.info("Start balance: {} - {} :End balance", start, end);

                if (!start.equals(end)) {
                    long id = listOfAddressesData.get(index).getId();

                    BigDecimal previousBalance = balanceService.getEthBalanceById(id);  // this the one not working

                    log.info("Previous balance: {}", previousBalance.toString());

                    BigDecimal finalBalance = end.subtract(start).add(previousBalance);

                    log.info("Final balance {}", finalBalance.toString());

                    balanceService.updateBalanceById(id, finalBalance);

                    toRemoveAdress.add(address);
                    toRemoveData.add(listOfAddressesData.get(index));

                    addressMap.put(address, false); // is it ok? must wait for all? no - it's scheduled
                }
            }
        }
            listOfAddresses.removeAll(toRemoveAdress);
            listOfAddressesData.removeAll(toRemoveData);
    }

    public Map<ETHAddress, Boolean> init() {
        log.info("Init eth adresses");

        List<ETHAddress> ethAddresses = transactionService.getAdresses();

        Map<ETHAddress, Boolean> auxAddressMap = new HashMap<ETHAddress, Boolean>();

        for (ETHAddress address :
                ethAddresses) {
            auxAddressMap.put(address, false);

            log.info("Adress {} added into map", address.getAddress());
        }

        return auxAddressMap;
    }

    private ETHAddress addressDistribution() {
        if (addressMap.containsValue(false)) {
            for (Map.Entry<ETHAddress, Boolean> entry : addressMap.entrySet()) {
                if (entry.getValue() == false) {
                    addressMap.put(entry.getKey(), true);

                    return entry.getKey();
                }
            }
        }
        return null; // create adress and save it into the database
    }

    //possible deposits

    @PostMapping("/deposit")
    public void depositEth(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String token = authorizationHeader.substring("Bearer ".length());

                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

                JWTVerifier verifier = JWT.require(algorithm).build();

                DecodedJWT decodedJWT = verifier.verify(token);

                String username = decodedJWT.getSubject();

                log.info("Username User resource - {} - asked for deposit", username);

                long id = balanceService.getBalanceByUsername(username).getId();

                // address distubution

                ETHAddress addressForDeposit = addressDistribution();

                log.info("Adress given: {}", addressForDeposit.getAddress());

                // add address for wait

                listOfAddresses.add(addressForDeposit);

                listOfAddressesData.add(new AddressData(addressUtil.getBalance(addressForDeposit.getAddress()), id));

                // send to user

                Map<String, String> deposit = new HashMap<>();

                deposit.put("adress", addressForDeposit.getAddress() + "");

                log.info(addressForDeposit.getAddress() + " ETH adress");

                response.setContentType(APPLICATION_JSON_VALUE);

                new ObjectMapper().writeValue(response.getOutputStream(), deposit);

            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());

                //response.sendError(FORBIDDEN.value());

                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);

                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("token is missing");
        }
    }

    // start of every month, send to mother adress

    @PostMapping("/withdraw") // could be 0
    public void withdrawEth(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String token = authorizationHeader.substring("Bearer ".length());

                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

                JWTVerifier verifier = JWT.require(algorithm).build();

                DecodedJWT decodedJWT = verifier.verify(token);

                String username = decodedJWT.getSubject();

                log.info("Username User resource - {} - asked for balance", username);

                Balance balance = balanceService.getBalanceByUsername(username);

                Map<String, String> bal = new HashMap<>();
                bal.put("eth", balance.getETH_BAL() + "");

                log.info(balance.getETH_BAL() + " ETH");

                bal.put("fiat", balance.getFIAT_BAL() + "");

                log.info(balance.getETH_BAL() + " FIAT");

                response.setContentType(APPLICATION_JSON_VALUE);

                new ObjectMapper().writeValue(response.getOutputStream(), bal);

            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());

                //response.sendError(FORBIDDEN.value());

                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);

                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("token is missing");
        }
    }

    @Data
    @AllArgsConstructor
    class AddressData {
        private BigDecimal startBalance;
        private long id;
    }
}

