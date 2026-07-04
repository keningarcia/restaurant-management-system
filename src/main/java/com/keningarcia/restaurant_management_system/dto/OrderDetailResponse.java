package com.keningarcia.restaurant_management_system.dto;

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
