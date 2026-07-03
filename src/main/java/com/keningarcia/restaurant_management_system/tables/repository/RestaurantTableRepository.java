package com.keningarcia.restaurant_management_system.tables.repository;

import com.keningarcia.restaurant_management_system.tables.entity.RestaurantTable;
import com.keningarcia.restaurant_management_system.tables.enums.TableStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long>, JpaSpecificationExecutor<RestaurantTable> {
    List<RestaurantTable> findByStatus(TableStatus status);
    boolean existsByTableNumber(String tableNumber);
}
