package com.example.cafeinventory.dto.request;

import com.example.cafeinventory.data.entity.enums.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import lombok.Data;


/*
    Handles creation of new Ingredient. So that the Entity classes are not directly exposed
    to API clients.
*/

@Data
public class IngredientCreateRequest {
    @NotBlank
    private String name;

    @NotNull
    private ItemCategory category;

//    Is the item a DRINK or FOOD
    private PerishableGroup perishableGroup;

//    for times when the item is in perishableGroup is DRINKS
    private IngredientType ingredientType;

    @NotNull
    private UnitOfMeasure unit; //ML or COUNT

    @NotNull
    @Min(0)
    private Integer quantity;

    @NotNull
    @Min(0)
    private Integer threshold;
}
