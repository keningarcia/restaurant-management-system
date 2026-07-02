package com.keningarcia.restaurant_management_system.auth.dto;

public record AuthResponse(
        String token,
        String type,
        Long id,
        String username,
        String email,
        String fullName
) {}
