package com.keningarcia.restaurant_management_system.purchases.repository;

import com.keningarcia.restaurant_management_system.purchases.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findBySupplierId(Long supplierId);
}
