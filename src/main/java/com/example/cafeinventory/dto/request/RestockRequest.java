package com.example.cafeinventory.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO for restocking an ingredient.
 */
@Data
public class RestockRequest {

    @NotNull
    @Min(1)
    private Integer amountAdded;

    // Optional note (supplier delivery, Costco run, etc.)
    private String note;
}