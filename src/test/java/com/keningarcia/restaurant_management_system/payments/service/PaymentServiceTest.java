package com.keningarcia.restaurant_management_system.payments.service;

import com.keningarcia.restaurant_management_system.orders.entity.Order;
import com.keningarcia.restaurant_management_system.orders.repository.OrderRepository;
import com.keningarcia.restaurant_management_system.payments.dto.PaymentRequest;
import com.keningarcia.restaurant_management_system.payments.dto.PaymentResponse;
import com.keningarcia.restaurant_management_system.payments.entity.Payment;
import com.keningarcia.restaurant_management_system.payments.enums.PaymentMethod;
import com.keningarcia.restaurant_management_system.payments.enums.PaymentStatus;
import com.keningarcia.restaurant_management_system.payments.mapper.PaymentMapper;
import com.keningarcia.restaurant_management_system.payments.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock private PaymentRepository paymentRepository;
    @Mock private OrderRepository orderRepository;
    @Mock private PaymentMapper paymentMapper;

    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        paymentService = new PaymentService(paymentRepository, orderRepository, paymentMapper);
    }

    @Test
    void findByOrderId_WhenExists_ReturnsPayment() {
        var payment = Payment.builder().id(1L).amount(BigDecimal.valueOf(50)).build();
        var response = new PaymentResponse(1L, 1L, 1L, BigDecimal.valueOf(50), "CASH",
                "COMPLETED", null, null, true, null);

        when(paymentRepository.findByOrderId(1L)).thenReturn(Optional.of(payment));
        when(paymentMapper.toResponse(payment)).thenReturn(response);

        var result = paymentService.findByOrderId(1L);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(50), result.amount());
    }

    @Test
    void create_WhenOrderExists_CreatesPayment() {
        var order = Order.builder().id(1L).total(BigDecimal.valueOf(100)).build();
        var request = new PaymentRequest(1L, BigDecimal.valueOf(100), "CASH", "COMPLETED", "REF001");

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(paymentRepository.save(any(Payment.class)))
                .thenReturn(Payment.builder().id(1L).order(order)
                        .amount(BigDecimal.valueOf(100)).paymentMethod(PaymentMethod.CASH)
                        .status(PaymentStatus.COMPLETED).build());

        paymentService.create(request);

        verify(paymentRepository).save(any(Payment.class));
    }
}
