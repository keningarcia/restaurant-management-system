package com.keningarcia.restaurant_management_system.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        Long tableId,
        String tableNumber,
        Long customerId,
        String customerName,
        Long employeeId,
        String employeeName,
        String status,
        BigDecimal subtotal,
        BigDecimal tax,
        BigDecimal total,
        String notes,
        List<OrderDetailResponse> details,
        Boolean active,
        LocalDateTime createdAt
) {}
