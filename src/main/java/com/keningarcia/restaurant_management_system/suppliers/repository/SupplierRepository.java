package com.keningarcia.restaurant_management_system.suppliers.repository;

import com.keningarcia.restaurant_management_system.suppliers.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    boolean existsByName(String name);
}
