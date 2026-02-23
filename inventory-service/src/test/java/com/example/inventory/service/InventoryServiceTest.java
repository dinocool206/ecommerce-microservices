package com.example.inventory.service;


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

        InventoryBatch batch = new InventoryBatch();
        batch.setBatchId(1L);
        batch.setProductId(1001L);
        batch.setQuantity(10);
        batch.setExpiryDate(LocalDate.now());

        when(factory.getStrategy("EXPIRY")).thenReturn(strategy);
        when(strategy.getSortedBatches(1001L)).thenReturn(List.of(batch));

        List<InventoryBatch> result = service.getInventory(1001L);

        assertEquals(1, result.size());
        verify(strategy, times(1)).getSortedBatches(1001L);
    }

    @Test
    void shouldThrowExceptionWhenStockInsufficient() {

        InventoryBatch batch = new InventoryBatch();
        batch.setBatchId(1L);
        batch.setQuantity(5);

        when(factory.getStrategy("EXPIRY")).thenReturn(strategy);
        when(strategy.getSortedBatches(1001L)).thenReturn(List.of(batch));

        assertThrows(RuntimeException.class,
                () -> service.updateInventory(1001L, 10));
    }
}
