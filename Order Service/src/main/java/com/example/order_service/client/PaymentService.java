package com.example.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "paymentClient", url = "${services.paynent.url}")
public interface PaymentService {
    @GetMapping("/paynent/process")
    String processPayment();
}