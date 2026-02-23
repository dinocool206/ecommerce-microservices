package com.example.inventory.strategy;

import com.example.inventory.entity.InventoryBatch;
import com.example.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpiryBasedInventoryStrategy
        implements InventoryStrategy {

    private final InventoryRepository repository;

    @Override
    public List<InventoryBatch>
    getSortedBatches(Long productId) {
        return repository
                .findByProductIdOrderByExpiryDateAsc(productId);
    }
}
