package com.license.CryptoBank.API;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.license.CryptoBank.Database.Entities.Balances;
import com.license.CryptoBank.Database.Entities.ETHAddress;
import com.license.CryptoBank.Database.Service.Balances.BalancesService;
import com.license.CryptoBank.Database.Service.Transaction.TransactionService;
import com.license.CryptoBank.Database.Service.User.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
public class TransactionResource {
    private final BalancesService balancesService;
    private final TransactionService transactionService;

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

                Balances balance = balancesService.getBalanceByUsername(username);

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

    @PostMapping("/deposit") // could be 0
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

                List<ETHAddress> ethAddresses = transactionService.getAdresses();

                Map<String, String> deposit = new HashMap<>();
                deposit.put("adress", ethAddresses.get(0).getAddress() + "");

                log.info(ethAddresses.get(0).getAddress() + " ETH adress");

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
}
