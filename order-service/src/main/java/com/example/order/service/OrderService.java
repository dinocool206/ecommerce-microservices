package com.example.order.service;

import com.example.order.client.InventoryClient;
import com.example.order.entity.Order;
import com.example.order.repository.OrderRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;



@Service
public class OrderService {

    private final OrderRepository repository;
    private final InventoryClient client;

    public OrderService(OrderRepository repository, InventoryClient client) {
        this.repository = repository;
        this.client = client;
    }

    public Order placeOrder(Long productId, Integer quantity) {

        List<InventoryClient.InventoryBatchDTO> batches = client.getInventory(productId);

        int total = batches.stream()
                .mapToInt(b -> b.quantity)
                .sum();

        if (total < quantity) throw new RuntimeException("Insufficient stock");

        client.updateInventory(productId, quantity);

        Order order = new Order();
        order.setProductId(productId);
        order.setQuantity(quantity);
        order.setStatus("PLACED");
        order.setOrderDate(LocalDate.now());

        return repository.save(order);
    }
}