package com.example.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "paymentClient", url = "${services.payment.url}")
public interface PaymentService {
    @GetMapping("/payment/process")
    String processPayment();
}