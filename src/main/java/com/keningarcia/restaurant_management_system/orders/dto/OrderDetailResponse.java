package com.keningarcia.restaurant_management_system.orders.dto;

import java.math.BigDecimal;

public record OrderDetailResponse(
        Long id,
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal subtotal,
        String notes
) {}
