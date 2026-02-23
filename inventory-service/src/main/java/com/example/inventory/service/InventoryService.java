package com.example.inventory.service;

import com.example.inventory.entity.InventoryBatch;
import com.example.inventory.factory.InventoryStrategyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private static final Logger logger =
            LoggerFactory.getLogger(InventoryService.class);

    private final InventoryStrategyFactory factory;

    public List<InventoryBatch> getInventory(Long productId) {

        return factory.getStrategy("EXPIRY")
                .getSortedBatches(productId);
    }

    @Transactional
    public void updateInventory(Long productId,
                                Integer quantity) {

        List<InventoryBatch> batches =
                getInventory(productId);

        int remaining = quantity;

        for (InventoryBatch batch : batches) {
            if (remaining <= 0) break;

            int available = batch.getQuantity();
            int deducted = Math.min(available, remaining);

            batch.setQuantity(available - deducted);
            logger.info("Batch: {}",batch);
            remaining -= deducted;
        }
    }
}
