package com.keningarcia.restaurant_management_system.repository;

import com.keningarcia.restaurant_management_system.entity.Order;
import com.keningarcia.restaurant_management_system.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    List<Order> findByStatus(OrderStatus status);
    List<Order> findByTableId(Long tableId);
}
