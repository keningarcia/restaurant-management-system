package com.keningarcia.restaurant_management_system.kitchen.dto;

import java.time.LocalDateTime;
import java.util.List;

public record KitchenOrderResponse(
        Long orderId,
        String tableNumber,
        String status,
        List<KitchenDetail> details,
        String notes,
        LocalDateTime createdAt
) {
    public record KitchenDetail(
            String productName,
            Integer quantity,
            String notes
    ) {}
}
