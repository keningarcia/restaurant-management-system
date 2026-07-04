package com.keningarcia.restaurant_management_system.controller;

import com.keningarcia.restaurant_management_system.dto.KitchenOrderResponse;
import com.keningarcia.restaurant_management_system.service.KitchenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/kitchen")
@RequiredArgsConstructor
public class KitchenController {

    private final KitchenService kitchenService;

    @GetMapping("/pending")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    public ResponseEntity<List<KitchenOrderResponse>> getPending() {
        return ResponseEntity.ok(kitchenService.getPendingOrders());
    }

    @GetMapping("/preparing")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    public ResponseEntity<List<KitchenOrderResponse>> getInPreparation() {
        return ResponseEntity.ok(kitchenService.getInPreparationOrders());
    }

    @PostMapping("/{orderId}/start")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    public ResponseEntity<KitchenOrderResponse> startPreparation(@PathVariable Long orderId) {
        return ResponseEntity.ok(kitchenService.startPreparation(orderId));
    }

    @PostMapping("/{orderId}/ready")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    public ResponseEntity<KitchenOrderResponse> markAsReady(@PathVariable Long orderId) {
        return ResponseEntity.ok(kitchenService.markAsReady(orderId));
    }
}
