package com.license.CryptoBank.Database.Service.InfuraUtil;

import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Optional;
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

    private static boolean validAddress(String ethereumAddress) {
        String regex = "^0x[0-9a-f]{40}$";
        if (ethereumAddress.matches(regex)) {
            return true;
        }
        return false;
    }

    private boolean checksumAddress(String ethereumAddress) {
        String subAddr = ethereumAddress.substring(2);

        String subAddrLower = subAddr.toLowerCase();

        SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest256();
        digestSHA3.update(subAddrLower.getBytes());
        String digestMessage = Hex.toHexString(digestSHA3.digest());

        for (short i = 0; i < subAddr.length(); i++) {
            if (subAddr.charAt(i) >= 65 && subAddr.charAt(i) <= 91) {
                String ss = Character.toString(digestMessage.charAt(i));
                if (!(Integer.parseInt(ss, 16) > 7)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void executeTransaction(String address, BigDecimal ammount) {
        final Web3j client = Web3j.build(new HttpService("https://ropsten.infura.io/v3/11a624c953e24db19f72ebedf4170ef8"));

        try {
            String pk = ""; // Add a private key here

            Credentials credentials = Credentials.create(pk);
            System.out.println("Account address: " + credentials.getAddress());
            System.out.println("Balance: " + Convert.fromWei(client.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance().toString(), Convert.Unit.ETHER));

            EthGetTransactionCount ethGetTransactionCount = client.ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.LATEST).send();
            BigInteger nonce = ethGetTransactionCount.getTransactionCount();

            String recipientAddress = address;

            /*
            BigInteger value = Convert.toWei(
                Convert.fromWei(client.ethGetBalance(credentials.getAddress(),
                    DefaultBlockParameterName.LATEST).send().getBalance().toString(),
                        Convert.Unit.ETHER).round(new MathContext(2,
                            RoundingMode.DOWN)).toString(),
                                Convert.Unit.ETHER).toBigInteger();
            */

            BigInteger value = Convert.toWei(ammount,Convert.Unit.ETHER).toBigInteger();

            BigInteger gasLimit = BigInteger.valueOf(21000);
            BigInteger gasPrice = client.ethGasPrice().send().getGasPrice();

            RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
                    nonce,
                    gasPrice,
                    gasLimit,
                    recipientAddress,
                    value);

            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signedMessage);

            EthSendTransaction ethSendTransaction = client.ethSendRawTransaction(hexValue).send();
           // String transactionHash = ethSendTransaction.getTransactionHash();

            //Optional<TransactionReceipt> transactionReceipt = null;
            //do {
             //   log.info("checking if transaction " + transactionHash + " is mined....");
            //    EthGetTransactionReceipt ethGetTransactionReceiptResp = client.ethGetTransactionReceipt(transactionHash).send();
             //   transactionReceipt = ethGetTransactionReceiptResp.getTransactionReceipt();
             //   Thread.sleep(3000); // Wait 3 sec
           // } while (!transactionReceipt.isPresent());

            //System.out.println("Transaction " + transactionHash + " was mined in block # " + transactionReceipt.get().getBlockNumber());
            //System.out.println("Balance: " + Convert.fromWei(client.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance().toString(), Convert.Unit.ETHER));


        } catch (IOException
                // | InterruptedException
                ex) {
            throw new RuntimeException(ex);
        }

    }

}
