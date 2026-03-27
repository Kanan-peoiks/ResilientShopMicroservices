package com.example.paynent_service.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

@RestController
public class PaymentController {

    @GetMapping("/paynent/process")
    public String processPayment() {
        if (ThreadLocalRandom.current().nextInt(10) < 4) {
            throw new RuntimeException("Payment service random failure");
        }
        return "Payment processed";
    }
}