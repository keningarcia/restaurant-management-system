package com.keningarcia.restaurant_management_system.suppliers.repository;

import com.keningarcia.restaurant_management_system.suppliers.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SupplierRepository extends JpaRepository<Supplier, Long>, JpaSpecificationExecutor<Supplier> {
    boolean existsByName(String name);
}
