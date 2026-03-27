package com.example.order_service.service;


import com.example.order_service.client.InventoryService;
import com.example.order_service.client.PaymentService;
import com.example.order_service.dto.OrderRequest;
import com.example.order_service.dto.OrderResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final InventoryService inventoryService;
    private final PaymentService paymentService;

    // 🔥 ƏSAS HİSSƏ
    private final OrderService self;

    public OrderService(InventoryService inventoryService,
                        PaymentService paymentService,
                        @Lazy OrderService self) {
        this.inventoryService = inventoryService;
        this.paymentService = paymentService;
        this.self = self;
    }

    // 🔹 MAIN FLOW
    public OrderResponse createOrder(OrderRequest request) {

        boolean available = self.checkInventory(request.productId());

        if (!available) {
            return new OrderResponse("FAILED", "Stock yoxdur");
        }

        String payment = self.processPayment();

        return new OrderResponse("SUCCESS", payment);
    }

    // 🔹 INVENTORY
    @Retry(name = "inventory")
    @CircuitBreaker(name = "inventory", fallbackMethod = "inventoryFallback")
    public boolean checkInventory(Long productId) {
        Boolean result = inventoryService.checkInventory(productId);
        return Boolean.TRUE.equals(result);
    }

    // 🔹 PAYMENT
    @CircuitBreaker(name = "payment", fallbackMethod = "paymentFallback")
    public String processPayment() {
        return paymentService.processPayment();
    }

    // 🔻 FALLBACKLAR

    public boolean inventoryFallback(Long productId, Throwable ex) {
        System.out.println("Inventory fallback işə düşdü");
        return false;
    }

    public String paymentFallback(Throwable ex) {
        System.out.println("Payment fallback işə düşdü");
        return "Payment skipped";
    }
}