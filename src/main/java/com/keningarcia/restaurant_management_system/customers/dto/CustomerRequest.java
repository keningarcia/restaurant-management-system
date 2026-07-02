package com.keningarcia.restaurant_management_system.customers.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerRequest(
        @NotBlank @Size(max = 100) String fullName,
        @Size(max = 20) String phone,
        @Size(max = 100) String email,
        @Size(max = 150) String address,
        @Size(max = 20) String documentNumber
) {}
