package com.keningarcia.restaurant_management_system.inventory.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record InventoryResponse(
        Long id,
        Long productId,
        String productName,
        Integer quantity,
        String unit,
        BigDecimal unitPrice,
        Long supplierId,
        String supplierName,
        LocalDate expirationDate,
        Integer minimumStock,
        Boolean active,
        LocalDateTime createdAt
) {}
