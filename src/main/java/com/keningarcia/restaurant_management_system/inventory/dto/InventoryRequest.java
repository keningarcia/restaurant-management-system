package com.keningarcia.restaurant_management_system.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record InventoryRequest(
        @NotNull Long productId,
        @NotNull @Positive Integer quantity,
        @NotBlank String unit,
        BigDecimal unitPrice,
        Long supplierId,
        LocalDate expirationDate,
        @NotNull @Positive Integer minimumStock
) {}
