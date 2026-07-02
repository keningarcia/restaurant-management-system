package com.keningarcia.restaurant_management_system.inventory.repository;

import com.keningarcia.restaurant_management_system.inventory.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByProductId(Long productId);
}
