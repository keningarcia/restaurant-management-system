package com.keningarcia.restaurant_management_system.purchases.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record PurchaseResponse(
        Long id,
        Long supplierId,
        String supplierName,
        LocalDate purchaseDate,
        BigDecimal totalAmount,
        String status,
        String notes,
        String invoiceNumber,
        Boolean active,
        LocalDateTime createdAt
) {}
