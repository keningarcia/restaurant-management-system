package com.keningarcia.restaurant_management_system.users.dto;

import java.time.LocalDateTime;
import java.util.Set;

public record UserResponse(
        Long id,
        String username,
        String email,
        String fullName,
        String status,
        Set<String> roles,
        Boolean active,
        LocalDateTime createdAt
) {}
