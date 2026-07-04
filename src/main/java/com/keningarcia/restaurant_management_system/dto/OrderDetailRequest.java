package com.keningarcia.restaurant_management_system.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record OrderDetailRequest(
        @NotNull Long productId,
        @NotNull @Positive Integer quantity,
        @NotNull BigDecimal unitPrice,
        String notes
) {}
