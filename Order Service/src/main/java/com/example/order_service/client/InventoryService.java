package com.example.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inventoryClient", url = "${services.inventory.url}")
public interface InventoryService {
    @GetMapping("/inventory/check/{productId}")
    Boolean checkInventory(@PathVariable("productId") Long productId);
}
