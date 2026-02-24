package com.example.inventory.service;


import com.example.inventory.dto.InventoryResponse;
import com.example.inventory.entity.InventoryBatch;
import com.example.inventory.factory.InventoryStrategyFactory;
import com.example.inventory.strategy.InventoryStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private InventoryStrategyFactory factory;

    @Mock
    private InventoryStrategy strategy;

    @InjectMocks
    private InventoryService service;

    @Test
    void shouldReturnSortedInventory() {

        // Arrange
        InventoryBatch batch = new InventoryBatch();
        batch.setBatchId(1L);
        batch.setProductId(1001L);
        batch.setProductName("Laptop");
        batch.setQuantity(10);
        batch.setExpiryDate(LocalDate.now());

        when(factory.getStrategy("EXPIRY")).thenReturn(strategy);
        when(strategy.getSortedBatches(1001L))
                .thenReturn(List.of(batch));

        // Act
        InventoryResponse result = service.getInventory(1001L);

        // Assert
        assertNotNull(result);
        assertEquals(1001L, result.getProductId());
        assertEquals("Laptop", result.getProductName());
        assertEquals(1, result.getBatches().size());
        assertEquals(1L, result.getBatches().get(0).getBatchId());

        verify(strategy, times(1))
                .getSortedBatches(1001L);
    }

    @Test
    void shouldReturnNullWhenNoInventoryFound() {

        when(factory.getStrategy("EXPIRY"))
                .thenReturn(strategy);

        when(strategy.getSortedBatches(1001L))
                .thenReturn(List.of());

        InventoryResponse response =
                service.getInventory(1001L);

        assertNull(response);
    }

    @Test
    void shouldUpdateInventorySuccessfully() {

        InventoryBatch batch = new InventoryBatch();
        batch.setBatchId(1L);
        batch.setQuantity(10);

        when(factory.getStrategy("EXPIRY"))
                .thenReturn(strategy);

        when(strategy.getSortedBatches(1001L))
                .thenReturn(List.of(batch));

        service.updateInventory(1001L, 5);

        assertEquals(5, batch.getQuantity());
    }
    @Test
    void shouldConsumeMultipleBatches() {

        InventoryBatch batch1 = new InventoryBatch();
        batch1.setBatchId(1L);
        batch1.setQuantity(5);

        InventoryBatch batch2 = new InventoryBatch();
        batch2.setBatchId(2L);
        batch2.setQuantity(10);

        when(factory.getStrategy("EXPIRY"))
                .thenReturn(strategy);

        when(strategy.getSortedBatches(1001L))
                .thenReturn(List.of(batch1, batch2));

        service.updateInventory(1001L, 8);

        assertEquals(0, batch1.getQuantity());
        assertEquals(7, batch2.getQuantity());
    }

}
