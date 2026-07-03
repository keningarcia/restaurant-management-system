package com.keningarcia.restaurant_management_system.reports.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SalesReportResponse(
        LocalDate startDate,
        LocalDate endDate,
        Long totalOrders,
        BigDecimal totalRevenue,
        BigDecimal totalTax,
        BigDecimal averageOrderValue
) {}
