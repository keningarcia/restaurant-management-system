package com.keningarcia.restaurant_management_system.dto;

import java.time.LocalDateTime;

public record RestaurantTableResponse(
        Long id,
        String tableNumber,
        Integer capacity,
        String location,
        String status,
        Boolean active,
        LocalDateTime createdAt
) {}
