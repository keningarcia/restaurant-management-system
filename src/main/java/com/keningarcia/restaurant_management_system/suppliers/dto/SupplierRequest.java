package com.keningarcia.restaurant_management_system.suppliers.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SupplierRequest(
        @NotBlank @Size(max = 100) String name,
        @Size(max = 20) String phone,
        @Size(max = 100) String email,
        @Size(max = 150) String address,
        @Size(max = 50) String contactPerson,
        @Size(max = 20) String documentNumber
) {}
