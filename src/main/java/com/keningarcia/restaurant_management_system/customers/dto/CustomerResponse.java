package com.keningarcia.restaurant_management_system.customers.dto;

import java.time.LocalDateTime;

public record CustomerResponse(
        Long id,
        String fullName,
        String phone,
        String email,
        String address,
        String documentNumber,
        Boolean active,
        LocalDateTime createdAt
) {}
