package com.example.cafeinventory.data.entity;

import com.example.cafeinventory.data.entity.base.AuditableEntity;
import com.example.cafeinventory.data.entity.enums.*;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "ingredients",
        indexes = {
        @Index(name="idx_ingredient_name", columnList = "name", unique = true),
        @Index(name="idx_ingredients_category", columnList = "category")
        }
)
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ingredient extends AuditableEntity {
    //unique id for each ingredient
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ingredient_id")
    private Long id;

    //EG: 2%Milk, Almond milk, espresso
    @Column(nullable = false)
    @NotBlank
    private String name;

//    If the item is perishable or non-perishable
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemCategory category;

//    Is the item a food or a drink
    @Enumerated(EnumType.STRING)
    private PerishableGroup perishableGroup;

//    Only relevant for drinks(as of right now).
//    Ingredients used to make drinks.
    @Enumerated(EnumType.STRING)
    private IngredientType ingredientType;

//    ML or COUNT depending on the item
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnitOfMeasure unitOfMeasure;

//    Quantity of the item
    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Integer quantity;

//    Threshold for an item in the inventory
    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Integer threshold;

}
