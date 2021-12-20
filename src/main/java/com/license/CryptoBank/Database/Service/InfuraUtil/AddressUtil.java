package com.license.CryptoBank.Database.Service.InfuraUtil;

import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class AddressUtil {  // not working
    public BigDecimal getBalance(String adress) throws ExecutionException, InterruptedException, TimeoutException {
        final Web3j client = Web3j.build(
                new HttpService(
                        "https://ropsten.infura.io/v3/11a624c953e24db19f72ebedf4170ef8"
                )
        );

        final EthGetBalance balanceResponse = client.ethGetBalance(adress, DefaultBlockParameter.valueOf("latest")).sendAsync().get(10, TimeUnit.SECONDS);

        final BigInteger unscaledBalance = balanceResponse.getBalance();

        final BigDecimal scaledBalance = new BigDecimal(unscaledBalance).divide(new BigDecimal(1000000000000000000L), 18, RoundingMode.HALF_UP);

        return scaledBalance;
    }

}
