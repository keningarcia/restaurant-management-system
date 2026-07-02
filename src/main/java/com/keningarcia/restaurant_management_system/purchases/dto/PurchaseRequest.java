package com.keningarcia.restaurant_management_system.purchases.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PurchaseRequest(
        @NotNull Long supplierId,
        @NotNull LocalDate purchaseDate,
        @NotNull @Positive BigDecimal totalAmount,
        @NotBlank String status,
        @Size(max = 500) String notes,
        @NotBlank @Size(max = 50) String invoiceNumber
) {}
