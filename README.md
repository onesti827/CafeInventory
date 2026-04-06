# Cafe Inventory Backend (Spring Boot + PostgreSQL)

NOTE: AI was used in developing the project.

---

A backend REST API for managing a cafe’s inventory. This service tracks item quantities, supports restocking (with transaction history), validates input, and returns consistent JSON error responses. Designed to be easy to extend (low-stock alerts, reporting, frontend integration, etc.).

---

## What this project does

### Inventory management
- Track inventory items and their quantities
- Store a reorder threshold per item
- Classify items:
  - **PERISHABLE** (DRINKS / FOOD)
  - **NON_PERISHABLE**

### Restocking
- Restock an item by adding to its quantity
- Automatically creates a **RestockTransaction** record (audit trail)
- Restock history is queryable per ingredient

### Validation + Error handling
- Uses Jakarta Bean Validation (`@Valid`) to enforce constraints (e.g., `quantity >= 0`)
- Uses a `GlobalExceptionHandler` (`@RestControllerAdvice`) to return consistent JSON error responses (`ApiError`) across all endpoints

---

## Tech stack
- **Java 17**
- **Spring Boot** (Web, Data JPA, Validation)
- **PostgreSQL**
- **Maven**
- **Lombok**
- IntelliJ IDEA (development/testing, including HTTP Client `.http` files)

---

## Project structure (packages)

- `com.example.cafeinventory.api.controller`
  - REST controllers (HTTP routes under `/api`)
- `com.example.cafeinventory.service.model`
  - Business logic (services)
- `com.example.cafeinventory.mapper`
  - DTO ↔ Entity mapping to keep services readable
- `com.example.cafeinventory.dto.request`
  - Request DTOs used by API endpoints
- `com.example.cafeinventory.data.entity`
  - JPA entities (Ingredient, RestockTransaction)
- `com.example.cafeinventory.data.entity.base`
  - Shared base classes (AuditableEntity)
- `com.example.cafeinventory.data.entity.enums`
  - Enums to enforce consistent values
- `com.example.cafeinventory.data.repository`
  - Spring Data JPA repositories
- `com.example.cafeinventory.exception`
  - Custom exceptions + global exception handler + ApiError response model

---

## Key design decisions (what we implemented)

### Enums
Enums prevent inconsistent strings like `"milk"`, `"Milk"`, `"MILK"` in the DB. We store enums as strings using `@Enumerated(EnumType.STRING)`.

Examples:
- `ItemCategory`: `PERISHABLE`, `NON_PERISHABLE`
- `PerishableGroup`: `DRINKS`, `FOOD`
- `IngredientType`: `COFFEE`, `MILK`, `TOPPING`, `WATER`, `SWEETENER`
- `UnitOfMeasure`: `ML`, `COUNT`

### AuditableEntity
All entities extend `AuditableEntity`, which automatically sets:
- `createdAt` on insert
- `updatedAt` on update

This is done using JPA lifecycle callbacks (`@PrePersist`, `@PreUpdate`).

### Mapper layer
We use a mapper (`IngredientMapper`) so service code focuses on business rules, not field-by-field assignment.

### Restock transactions
Restocking is not just changing quantity: every restock creates a `RestockTransaction` entry with:
- ingredient
- amountAdded
- previousQuantity
- newQuantity
- note (optional)

This makes audit/history and debugging easy.

### GlobalExceptionHandler
Services throw meaningful custom exceptions (e.g., `ResourceNotFoundException`), and the global handler converts them into consistent JSON responses.

---

## Running locally

### Prerequisites
- Java 17+
- Maven
- PostgreSQL

### Database
Create a database (example uses `cms`):
```sql
CREATE DATABASE cms;
