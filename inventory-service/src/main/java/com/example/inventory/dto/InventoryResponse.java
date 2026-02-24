package com.example.inventory.dto;

import lombok.ToString;

import java.util.List;
@ToString
public class InventoryResponse {

    private Long productId;
    private String productName;
    private List<BatchResponse> batches;

    public InventoryResponse(Long productId, String productName, List<BatchResponse> batches) {
        this.productId = productId;
        this.productName = productName;
        this.batches = batches;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public List<BatchResponse> getBatches() {
        return batches;
    }
}