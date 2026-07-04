package com.keningarcia.restaurant_management_system.repository;

import com.keningarcia.restaurant_management_system.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long>, JpaSpecificationExecutor<Purchase> {
    List<Purchase> findBySupplierId(Long supplierId);
}
