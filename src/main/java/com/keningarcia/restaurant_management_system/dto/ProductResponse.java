package com.keningarcia.restaurant_management_system.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Long categoryId,
        String categoryName,
        String status,
        String imageUrl,
        Boolean active,
        LocalDateTime createdAt
) {}
