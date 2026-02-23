package com.example.inventory.controller;

import com.example.inventory.service.InventoryService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService service;

    @GetMapping("/{productId}")
    public ResponseEntity<?> getInventory(@PathVariable Long productId) {
        return ResponseEntity.ok(service.getInventory(productId));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateInventory(@RequestBody UpdateRequest request) {
        service.updateInventory(request.getProductId(), request.getQuantity());
        return ResponseEntity.ok("Inventory updated");
    }
}

@Getter
@Setter
class UpdateRequest {
    private Long productId;
    private Integer quantity;

}
