package com.example.cafeinventory.dto.request;

import com.example.cafeinventory.data.entity.enums.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 ->DTO for updating an Ingredient.
 ->PUT replaces all fields. Later other options will be added
 */
@Data
public class IngredientUpdateRequest {

    @NotBlank
    private String name;

    @NotNull
    private ItemCategory category;

    private PerishableGroup perishableGroup;
    private IngredientType ingredientType;

    @NotNull
    private UnitOfMeasure unit;

    @NotNull
    @Min(0)
    private Integer quantity;

    @NotNull
    @Min(0)
    private Integer threshold;
}