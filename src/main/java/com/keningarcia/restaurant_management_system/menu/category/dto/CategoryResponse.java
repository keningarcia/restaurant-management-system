package com.keningarcia.restaurant_management_system.menu.category.dto;

import java.time.LocalDateTime;

public record CategoryResponse(
        Long id,
        String name,
        String description,
        Boolean active,
        LocalDateTime createdAt
) {}
