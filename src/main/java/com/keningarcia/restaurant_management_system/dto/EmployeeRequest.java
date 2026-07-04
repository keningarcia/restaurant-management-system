package com.keningarcia.restaurant_management_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeRequest(
        @NotNull Long userId,
        @NotBlank @Size(max = 20) String phone,
        @NotBlank @Size(max = 150) String address,
        @NotBlank String position,
        @NotNull LocalDate hireDate,
        @NotNull @Positive BigDecimal salary,
        @Size(max = 20) String documentNumber
) {}
