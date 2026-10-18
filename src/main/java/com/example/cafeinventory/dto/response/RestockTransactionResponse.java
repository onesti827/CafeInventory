package com.example.cafeinventory.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

/**
 * Response DTO for a restock transaction. The owning ingredient is represented
 * by its id only (Option A) — flat shape, no nested entity, no lazy-proxy leaks.
 */
@Data
@Builder
public class RestockTransactionResponse {
    private Long id;
    private Long ingredientId;
    private Integer amountAdded;
    private Integer previousQuantity;
    private Integer newQuantity;
    private String note;
    private Instant createdAt;
    private Instant updatedAt;
}
