package com.example.cafeinventory.api.controller;

import com.example.cafeinventory.data.entity.Ingredient;
import com.example.cafeinventory.data.entity.RestockTransaction;
import com.example.cafeinventory.dto.request.IngredientCreateRequest;
import com.example.cafeinventory.dto.request.IngredientUpdateRequest;
import com.example.cafeinventory.dto.request.RestockRequest;
import com.example.cafeinventory.service.model.IngredientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for ingredient endpoints.
 * Base route: /api/ingredients
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    @PostMapping
    public Ingredient create(@Valid @RequestBody IngredientCreateRequest req) {
        return ingredientService.create(req);
    }

    @PutMapping("/{id}")
    public Ingredient update(@PathVariable Long id, @Valid @RequestBody IngredientUpdateRequest req) {
        return ingredientService.update(id, req);
    }

    @GetMapping
    public List<Ingredient> listAll() {
        return ingredientService.listAll();
    }

    @PostMapping("/{id}/restock")
    public Ingredient restock(@PathVariable Long id, @Valid @RequestBody RestockRequest req) {
        return ingredientService.restock(id, req);
    }

    @GetMapping("/{id}/restocks")
    public List<RestockTransaction> restockHistory(@PathVariable Long id) {
        return ingredientService.restockHistory(id);
    }
}