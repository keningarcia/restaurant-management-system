package com.keningarcia.restaurant_management_system.repository;

import com.keningarcia.restaurant_management_system.entity.RestaurantTable;
import com.keningarcia.restaurant_management_system.enums.TableStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long>, JpaSpecificationExecutor<RestaurantTable> {
    List<RestaurantTable> findByStatus(TableStatus status);
    boolean existsByTableNumber(String tableNumber);
}
