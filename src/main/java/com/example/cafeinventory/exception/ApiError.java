package com.example.cafeinventory.exception;

import lombok.Builder;
import lombok.Value;
import java.time.Instant;
import java.util.Map;

//Error Response body for API
@Builder
@Value
public class ApiError{
    Instant timestamp;
    String message;
    String error;
    String path;
    int status;

    Map<String, String> fieldErrors;
}
