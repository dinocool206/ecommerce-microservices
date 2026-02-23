package com.example.order.service;

import com.example.order.client.InventoryClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import com.example.order.entity.Order;
import com.example.order.repository.OrderRepository;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository repository;

    @Mock
    private InventoryClient client;

    @InjectMocks
    private OrderService service;

    @Test
    void shouldPlaceOrderWhenStockAvailable() {

        InventoryClient.InventoryBatchDTO batch =
                new InventoryClient.InventoryBatchDTO();
        batch.quantity = 10;

        when(client.getInventory(1001L)).thenReturn(List.of(batch));
        when(repository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Order order = service.placeOrder(1001L, 5);

        assertEquals("PLACED", order.getStatus());
        verify(client).updateInventory(1001L, 5);
    }

    @Test
    void shouldThrowExceptionWhenStockInsufficient() {

        InventoryClient.InventoryBatchDTO batch =
                new InventoryClient.InventoryBatchDTO();
        batch.quantity = 2;

        when(client.getInventory(1001L)).thenReturn(List.of(batch));

        assertThrows(RuntimeException.class,
                () -> service.placeOrder(1001L, 5));
    }
}
