package com.example.cafeinventory.service.model;

import com.example.cafeinventory.data.entity.Ingredient;
import com.example.cafeinventory.data.entity.RestockTransaction;
import com.example.cafeinventory.data.entity.enums.IngredientType;
import com.example.cafeinventory.data.entity.enums.PerishableGroup;
import com.example.cafeinventory.data.repository.IngredientRepository;
import com.example.cafeinventory.data.repository.RestockTransactionRepository;
import com.example.cafeinventory.dto.request.IngredientCreateRequest;
import com.example.cafeinventory.dto.request.IngredientUpdateRequest;
import com.example.cafeinventory.dto.request.RestockRequest;
import com.example.cafeinventory.dto.response.IngredientResponse;
import com.example.cafeinventory.dto.response.RestockTransactionResponse;
import com.example.cafeinventory.exception.BadRequestException;
import com.example.cafeinventory.exception.DuplicateResourceException;
import com.example.cafeinventory.exception.ResourceNotFoundException;
import com.example.cafeinventory.mapper.IngredientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service layer = business logic.
 * Mapper handles DTO<->Entity conversion so service stays readable.
 * TODO (#8): paginate listAll() once the dataset grows beyond trivial size.
 */
@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final RestockTransactionRepository restockTransactionRepository;
    private final IngredientMapper ingredientMapper;

    @Transactional
    public IngredientResponse create(IngredientCreateRequest req) {
        // Unique name enforcement
        if (ingredientRepository.existsByNameIgnoreCase(req.getName())) {
            throw DuplicateResourceException.ingredientName(req.getName());
        }

        validatePerishableGroupAndType(req.getPerishableGroup(), req.getIngredientType());

        Ingredient ing = ingredientMapper.toEntity(req);
        return ingredientMapper.toResponse(ingredientRepository.save(ing));
    }

    @Transactional
    public IngredientResponse update(Long id, IngredientUpdateRequest req) {
        Ingredient existing = ingredientRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.ingredient(id));

        // If name changes, enforce uniqueness
        String newName = req.getName().trim();
        if (!existing.getName().equalsIgnoreCase(newName)
                && ingredientRepository.existsByNameIgnoreCase(newName)) {
            throw DuplicateResourceException.ingredientName(newName);
        }

        // Same FOOD/ingredientType guard as create — keeps the two paths consistent.
        validatePerishableGroupAndType(req.getPerishableGroup(), req.getIngredientType());

        ingredientMapper.updateEntity(existing, req);
        return ingredientMapper.toResponse(ingredientRepository.save(existing));
    }

    @Transactional
    public IngredientResponse restock(Long ingredientId, RestockRequest req) {
        Ingredient ing = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> ResourceNotFoundException.ingredient(ingredientId));

        int prev = ing.getQuantity();
        int next = prev + req.getAmountAdded();

        // Update current quantity
        ing.setQuantity(next);
        Ingredient saved = ingredientRepository.save(ing);

        // Store transaction history
        RestockTransaction tx = RestockTransaction.builder()
                .ingredient(saved)
                .amountAdded(req.getAmountAdded())
                .previousQuantity(prev)
                .newQuantity(next)
                .note(req.getNote())
                .build();

        restockTransactionRepository.save(tx);
        return ingredientMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<IngredientResponse> listAll() {
        return ingredientRepository.findAll().stream()
                .map(ingredientMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<RestockTransactionResponse> restockHistory(Long ingredientId) {
        return restockTransactionRepository.findByIngredientIdOrderByCreatedAtDesc(ingredientId).stream()
                .map(ingredientMapper::toResponse)
                .toList();
    }

    // Business rule: FOOD items should not carry an ingredientType (tracked ingredients are for DRINKS).
    private void validatePerishableGroupAndType(PerishableGroup group, IngredientType ingredientType) {
        if (group == PerishableGroup.FOOD && ingredientType != null) {
            throw new BadRequestException(
                    "FOOD items should not have ingredientType (tracked ingredients are for DRINKS).");
        }
    }
}
