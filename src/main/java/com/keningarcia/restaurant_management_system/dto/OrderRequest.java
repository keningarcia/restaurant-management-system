package com.keningarcia.restaurant_management_system.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record OrderRequest(
        @NotNull Long tableId,
        Long customerId,
        @NotNull Long employeeId,
        @Size(max = 500) String notes,
        @NotNull List<OrderDetailRequest> details
) {}
