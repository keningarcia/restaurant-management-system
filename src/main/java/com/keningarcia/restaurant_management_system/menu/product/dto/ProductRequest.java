package com.keningarcia.restaurant_management_system.menu.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank @Size(max = 50) String name,
        @Size(max = 500) String description,
        @NotNull @Positive BigDecimal price,
        @NotNull Long categoryId,
        @NotBlank String status,
        @Size(max = 255) String imageUrl
) {}
