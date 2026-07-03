package com.keningarcia.restaurant_management_system.payments.repository;

import com.keningarcia.restaurant_management_system.payments.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long>, JpaSpecificationExecutor<Payment> {
    Optional<Payment> findByOrderId(Long orderId);
}
