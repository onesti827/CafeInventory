package com.example.cafeinventory.mapper;

import com.example.cafeinventory.data.entity.Ingredient;
import com.example.cafeinventory.data.entity.RestockTransaction;
import com.example.cafeinventory.dto.request.IngredientCreateRequest;
import com.example.cafeinventory.dto.request.IngredientUpdateRequest;
import com.example.cafeinventory.dto.response.IngredientResponse;
import com.example.cafeinventory.dto.response.RestockTransactionResponse;
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

    /**
     * Entity -> response DTO. Keeps the JSON shape stable and decoupled from JPA.
     */
    public IngredientResponse toResponse(Ingredient ing) {
        if (ing == null) return null;
        return IngredientResponse.builder()
                .id(ing.getId())
                .name(ing.getName())
                .category(ing.getCategory())
                .perishableGroup(ing.getPerishableGroup())
                .ingredientType(ing.getIngredientType())
                .unit(ing.getUnitOfMeasure())
                .quantity(ing.getQuantity())
                .threshold(ing.getThreshold())
                .createdAt(ing.getCreatedAt())
                .updatedAt(ing.getUpdatedAt())
                .build();
    }

    /**
     * RestockTransaction entity -> response DTO. The ingredient is represented
     * by its id only, so no lazy proxy ever reaches Jackson.
     */
    public RestockTransactionResponse toResponse(RestockTransaction tx) {
        if (tx == null) return null;
        return RestockTransactionResponse.builder()
                .id(tx.getId())
                .ingredientId(tx.getIngredient() != null ? tx.getIngredient().getId() : null)
                .amountAdded(tx.getAmountAdded())
                .previousQuantity(tx.getPreviousQuantity())
                .newQuantity(tx.getNewQuantity())
                .note(tx.getNote())
                .createdAt(tx.getCreatedAt())
                .updatedAt(tx.getUpdatedAt())
                .build();
    }
}
