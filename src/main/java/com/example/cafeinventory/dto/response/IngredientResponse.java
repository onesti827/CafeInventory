package com.example.cafeinventory.dto.response;

import com.example.cafeinventory.data.entity.enums.IngredientType;
import com.example.cafeinventory.data.entity.enums.ItemCategory;
import com.example.cafeinventory.data.entity.enums.PerishableGroup;
import com.example.cafeinventory.data.entity.enums.UnitOfMeasure;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

/**
 * Response DTO for Ingredient. Keeps the API shape independent of the JPA entity,
 * so lazy-proxy fields and future entity-only columns never leak to clients.
 */
@Data
@Builder
public class IngredientResponse {
    private Long id;
    private String name;
    private ItemCategory category;
    private PerishableGroup perishableGroup;
    private IngredientType ingredientType;
    private UnitOfMeasure unit;
    private Integer quantity;
    private Integer threshold;
    private Instant createdAt;
    private Instant updatedAt;
}
