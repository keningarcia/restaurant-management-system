package com.keningarcia.restaurant_management_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record RestaurantTableRequest(
        @NotBlank @Size(max = 10) String tableNumber,
        @NotNull @Positive Integer capacity,
        @Size(max = 50) String location,
        @NotBlank String status
) {}
