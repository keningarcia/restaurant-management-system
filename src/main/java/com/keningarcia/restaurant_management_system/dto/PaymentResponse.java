package com.keningarcia.restaurant_management_system.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponse(
        Long id,
        Long orderId,
        Long orderNumber,
        BigDecimal amount,
        String paymentMethod,
        String status,
        LocalDateTime paymentDate,
        String referenceNumber,
        Boolean active,
        LocalDateTime createdAt
) {}
