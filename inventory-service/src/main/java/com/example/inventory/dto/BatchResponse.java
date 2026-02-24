package com.example.inventory.dto;

import lombok.ToString;

import java.time.LocalDate;
@ToString
public class BatchResponse {

    private Long batchId;
    private Integer quantity;
    private LocalDate expiryDate;

    public BatchResponse(Long batchId, Integer quantity, LocalDate expiryDate) {
        this.batchId = batchId;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
    }

    public Long getBatchId() {
        return batchId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }
}
