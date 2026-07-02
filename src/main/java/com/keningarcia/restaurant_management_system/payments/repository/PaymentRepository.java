package com.keningarcia.restaurant_management_system.payments.repository;

import com.keningarcia.restaurant_management_system.payments.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrderId(Long orderId);
}
