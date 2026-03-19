package com.example.cafeinventory.exception;

//Exception for a Bad Request(400)
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
