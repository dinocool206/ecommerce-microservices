package com.example.inventory.strategy;

import com.example.inventory.entity.InventoryBatch;

import java.util.List;

public interface InventoryStrategy {
    List<InventoryBatch> getSortedBatches(Long productId);
}
