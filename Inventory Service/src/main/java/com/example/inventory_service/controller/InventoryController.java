package com.example.inventory_service.controller;

import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @GetMapping("/check/{productId}")
    public Boolean checkInventory(@PathVariable Long productId) {
        if (ThreadLocalRandom.current().nextInt(10) < 3) {
            throw new RuntimeException("Inventory service random failure");
        }
        return true;
    }
}