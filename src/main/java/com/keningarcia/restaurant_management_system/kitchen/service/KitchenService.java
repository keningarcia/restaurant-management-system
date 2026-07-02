package com.keningarcia.restaurant_management_system.kitchen.service;

import com.keningarcia.restaurant_management_system.exceptions.ResourceNotFoundException;
import com.keningarcia.restaurant_management_system.kitchen.dto.KitchenOrderResponse;
import com.keningarcia.restaurant_management_system.orders.entity.Order;
import com.keningarcia.restaurant_management_system.orders.enums.OrderStatus;
import com.keningarcia.restaurant_management_system.orders.mapper.OrderMapper;
import com.keningarcia.restaurant_management_system.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KitchenService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Transactional(readOnly = true)
    public List<KitchenOrderResponse> getPendingOrders() {
        return orderRepository.findByStatus(OrderStatus.SENT_TO_KITCHEN)
                .stream()
                .map(this::toKitchenResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<KitchenOrderResponse> getInPreparationOrders() {
        return orderRepository.findByStatus(OrderStatus.PREPARING)
                .stream()
                .map(this::toKitchenResponse)
                .toList();
    }

    @Transactional
    public KitchenOrderResponse startPreparation(Long orderId) {
        var order = findOrder(orderId);
        order.setStatus(OrderStatus.PREPARING);
        return toKitchenResponse(orderRepository.save(order));
    }

    @Transactional
    public KitchenOrderResponse markAsReady(Long orderId) {
        var order = findOrder(orderId);
        order.setStatus(OrderStatus.READY);
        return toKitchenResponse(orderRepository.save(order));
    }

    private KitchenOrderResponse toKitchenResponse(Order order) {
        return new KitchenOrderResponse(
                order.getId(),
                order.getTable().getTableNumber(),
                order.getStatus().name(),
                order.getDetails().stream()
                        .map(d -> new KitchenOrderResponse.KitchenDetail(
                                d.getProduct().getName(),
                                d.getQuantity(),
                                d.getNotes()))
                        .toList(),
                order.getNotes(),
                order.getCreatedAt()
        );
    }

    private Order findOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado: " + id));
    }
}
