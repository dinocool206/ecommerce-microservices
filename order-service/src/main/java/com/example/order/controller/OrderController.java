package com.example.order.controller;

import com.example.order.entity.Order;
import com.example.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> place(@RequestBody OrderRequest req) {

        Order order = service.placeOrder(req.productId, req.quantity);

        OrderResponse response = new OrderResponse();
        response.orderId = order.getOrderId();
        response.productId = order.getProductId();
        response.quantity = order.getQuantity();
        response.status = order.getStatus();
        response.message = "Order placed. Inventory reserved.";

        return ResponseEntity.ok(response);
    }

    static class OrderRequest {
        public Long productId;
        public Integer quantity;
    }

    static class OrderResponse {
        public Long orderId;
        public Long productId;
        public Integer quantity;
        public String status;
        public String message;
    }
}
