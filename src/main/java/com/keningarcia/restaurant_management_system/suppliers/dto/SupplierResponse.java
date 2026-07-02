package com.keningarcia.restaurant_management_system.suppliers.dto;

import java.time.LocalDateTime;

public record SupplierResponse(
        Long id,
        String name,
        String phone,
        String email,
        String address,
        String contactPerson,
        String documentNumber,
        Boolean active,
        LocalDateTime createdAt
) {}
