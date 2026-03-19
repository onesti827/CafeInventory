package com.example.cafeinventory.data.entity;

import com.example.cafeinventory.data.entity.base.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

@Entity
@Table(name="restock_transactions",
        indexes = {
        @Index(name="idx_restock_ingredient_id", columnList = "ingredient_id")
        }
)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RestockTransaction extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many transactions per ingredient
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

//    Amount to add for restocking purposes
    @NotNull
    @Min(1)
    @Column(nullable = false)
    private Integer amountAdded;

//   Holds previous quantity for an item
    @NotNull
    @Column(nullable = false)
    private Integer previousQuantity;

//    Holds the new quantity for an item
    @NotNull
    @Column(nullable = false)
    private Integer newQuantity;
    private String note;

}
