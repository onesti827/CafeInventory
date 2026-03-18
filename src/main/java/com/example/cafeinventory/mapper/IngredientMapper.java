package com.example.cafeinventory.mapper;

import com.example.cafeinventory.data.entity.Ingredient;
import com.example.cafeinventory.dto.request.IngredientCreateRequest;
import com.example.cafeinventory.dto.request.IngredientUpdateRequest;
import org.springframework.stereotype.Component;

/**
 * Converts DTOs <-> Entities so service layer stays clean.
 */
@Component
public class IngredientMapper {

    /**
     * Create request DTO -> new Ingredient entity.
     */
    public Ingredient toEntity(IngredientCreateRequest req) {
        if (req == null) return null;

        return Ingredient.builder()
                .name(req.getName().trim())
                .category(req.getCategory())
                .perishableGroup(req.getPerishableGroup())
                .ingredientType(req.getIngredientType())
                .unitOfMeasure(req.getUnit())
                .quantity(req.getQuantity())
                .threshold(req.getThreshold())
                .build();
    }

    /**
     Update request -> update existing Ingredient entity.
    */
    public void updateEntity(Ingredient existing, IngredientUpdateRequest req) {
        if (existing == null || req == null) return;

        existing.setName(req.getName().trim());
        existing.setCategory(req.getCategory());
        existing.setPerishableGroup(req.getPerishableGroup());
        existing.setIngredientType(req.getIngredientType());
        existing.setUnitOfMeasure(req.getUnit());
        existing.setQuantity(req.getQuantity());
        existing.setThreshold(req.getThreshold());
    }
}