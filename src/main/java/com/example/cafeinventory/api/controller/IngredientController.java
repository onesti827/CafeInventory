package com.example.cafeinventory.api.controller;

import com.example.cafeinventory.dto.request.IngredientCreateRequest;
import com.example.cafeinventory.dto.request.IngredientUpdateRequest;
import com.example.cafeinventory.dto.request.RestockRequest;
import com.example.cafeinventory.dto.response.IngredientResponse;
import com.example.cafeinventory.dto.response.RestockTransactionResponse;
import com.example.cafeinventory.service.model.IngredientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for ingredient endpoints.
 * Base route: /api/ingredients
 *
 * Returns response DTOs rather than JPA entities, so Hibernate lazy proxies and
 * entity-only fields never reach the client.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    @PostMapping
    public IngredientResponse create(@Valid @RequestBody IngredientCreateRequest req) {
        return ingredientService.create(req);
    }

    @PutMapping("/{id}")
    public IngredientResponse update(@PathVariable Long id, @Valid @RequestBody IngredientUpdateRequest req) {
        return ingredientService.update(id, req);
    }

    @GetMapping
    public List<IngredientResponse> listAll() {
        return ingredientService.listAll();
    }

    @PostMapping("/{id}/restock")
    public IngredientResponse restock(@PathVariable Long id, @Valid @RequestBody RestockRequest req) {
        return ingredientService.restock(id, req);
    }

    @GetMapping("/{id}/restocks")
    public List<RestockTransactionResponse> restockHistory(@PathVariable Long id) {
        return ingredientService.restockHistory(id);
    }
}
