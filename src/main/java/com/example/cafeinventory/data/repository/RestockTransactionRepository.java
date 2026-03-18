package com.example.cafeinventory.data.repository;

import com.example.cafeinventory.data.entity.RestockTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface RestockTransactionRepository extends JpaRepository<RestockTransaction, Long> {
    List<RestockTransaction> findByIngredientIdOrderByCreatedAtDesc(Long id);
}
