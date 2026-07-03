package com.keningarcia.restaurant_management_system.inventory.repository;

import com.keningarcia.restaurant_management_system.inventory.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long>, JpaSpecificationExecutor<Inventory> {
    List<Inventory> findByProductId(Long productId);
}
