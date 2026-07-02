package com.keningarcia.restaurant_management_system.orders.repository;

import com.keningarcia.restaurant_management_system.orders.entity.Order;
import com.keningarcia.restaurant_management_system.orders.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    List<Order> findByStatus(OrderStatus status);
    List<Order> findByTableId(Long tableId);
}
