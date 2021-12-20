package com.license.CryptoBank.Service.InfuraUtil;

import com.license.CryptoBank.Database.Service.InfuraUtil.AddressUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AddressUtilTest {

    AddressUtil addressUtil;

    @BeforeEach
    void setUp() {
        addressUtil = new AddressUtil();
    }

    @Test
    @DisplayName("Get balance from ETH blockchain test")
    void getBalanceTest() throws ExecutionException, InterruptedException, TimeoutException {
        assertEquals(BigDecimal.valueOf(0E-18),addressUtil.getBalance("0xc57d751C013C8087e0FD71296f76cdBC31cDe388"), "Should return balance of address");
    }

    @Test
    @DisplayName("Zero equals Zero ETH blockchain test")
    void zeroEqualsZeroTest() throws ExecutionException, InterruptedException, TimeoutException {
        assertEquals(addressUtil.getBalance("0xc57d751C013C8087e0FD71296f76cdBC31cDe388"),addressUtil.getBalance("0xc57d751C013C8087e0FD71296f76cdBC31cDe388"), "Should say that 0E-18 == 0E-18");
    }

    @Test
    @DisplayName("Zero equals Zero Simple statement")
    void zeroEqualsZeroSimpleTest() throws ExecutionException, InterruptedException, TimeoutException {
        BigDecimal start = addressUtil.getBalance("0xc57d751C013C8087e0FD71296f76cdBC31cDe388");
        BigDecimal end = addressUtil.getBalance("0xc57d751C013C8087e0FD71296f76cdBC31cDe388");
        assertTrue(start.equals(end), "True Should say that 0E-18 == 0E-18");
        assertEquals(start, end, "Equals Should say that 0E-18 == 0E-18");

    }
}
