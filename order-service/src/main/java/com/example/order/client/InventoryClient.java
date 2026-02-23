package com.example.order.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

@Component
public class InventoryClient {

    private final WebClient client = WebClient.create("http://localhost:8081");

    public List<InventoryBatchDTO> getInventory(Long productId) {
        return client.get()
                .uri("/inventory/{id}", productId)
                .retrieve()
                .bodyToFlux(InventoryBatchDTO.class)
                .collectList()
                .block();
    }

    public void updateInventory(Long productId, Integer quantity) {
        client.post()
                .uri("/inventory/update")
                .bodyValue(new UpdateRequest(productId, quantity))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public static class InventoryBatchDTO {
        public Long batchId;
        public Integer quantity;
    }

    static class UpdateRequest {
        public Long productId;
        public Integer quantity;

        public UpdateRequest(Long p, Integer q) {
            productId = p;
            quantity = q;
        }
    }
}