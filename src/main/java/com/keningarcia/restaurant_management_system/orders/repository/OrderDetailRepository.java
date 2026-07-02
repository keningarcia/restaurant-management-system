package com.keningarcia.restaurant_management_system.orders.repository;

import com.keningarcia.restaurant_management_system.orders.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrderId(Long orderId);
}
