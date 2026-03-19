package com.example.cafeinventory.exception;

//Exception for checking duplicates and maintaining uniqueness(409)
public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }

    public static DuplicateResourceException ingredientName(String name){
        return new DuplicateResourceException("Ingredient name already exists: " + name);
    }
}
