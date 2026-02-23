package com.example.inventory.repository;

import com.example.inventory.entity.InventoryBatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository
        extends JpaRepository<InventoryBatch, Long> {

    List<InventoryBatch>
    findByProductIdOrderByExpiryDateAsc(Long productId);
}
