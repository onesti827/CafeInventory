package com.example.cafeinventory.exception;


//Not found exception for missing Ingredients (404)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public static ResourceNotFoundException ingredient(Long id){
        return new ResourceNotFoundException("Ingredient not found with id " + id);
    }
}
