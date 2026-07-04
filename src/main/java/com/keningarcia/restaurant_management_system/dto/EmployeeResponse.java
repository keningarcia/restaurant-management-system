package com.keningarcia.restaurant_management_system.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record EmployeeResponse(
        Long id,
        Long userId,
        String username,
        String phone,
        String address,
        String position,
        LocalDate hireDate,
        BigDecimal salary,
        String documentNumber,
        Boolean active,
        LocalDateTime createdAt
) {}
