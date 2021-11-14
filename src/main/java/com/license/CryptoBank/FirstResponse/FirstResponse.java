package com.license.CryptoBank.FirstResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstResponse {

    @GetMapping("/1")
    public String firstReturn() {
        return "compot";
    }
}
