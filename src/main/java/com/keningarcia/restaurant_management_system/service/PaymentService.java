package com.keningarcia.restaurant_management_system.service;

import com.keningarcia.restaurant_management_system.exceptions.ResourceNotFoundException;
import com.keningarcia.restaurant_management_system.repository.OrderRepository;
import com.keningarcia.restaurant_management_system.dto.PaymentRequest;
import com.keningarcia.restaurant_management_system.dto.PaymentResponse;
import com.keningarcia.restaurant_management_system.entity.Payment;
import com.keningarcia.restaurant_management_system.enums.PaymentMethod;
import com.keningarcia.restaurant_management_system.enums.PaymentStatus;
import com.keningarcia.restaurant_management_system.mapper.PaymentMapper;
import com.keningarcia.restaurant_management_system.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final PaymentMapper paymentMapper;

    @Transactional(readOnly = true)
    public Page<PaymentResponse> findAll(Pageable pageable) {
        return paymentRepository.findAll(pageable).map(paymentMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public PaymentResponse findById(Long id) {
        return paymentMapper.toResponse(findPayment(id));
    }

    @Transactional(readOnly = true)
    public PaymentResponse findByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId)
                .map(paymentMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado para orden: " + orderId));
    }

    @Transactional
    public PaymentResponse create(PaymentRequest request) {
        var order = orderRepository.findById(request.orderId())
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada: " + request.orderId()));

        var payment = Payment.builder()
                .order(order)
                .amount(request.amount())
                .paymentMethod(PaymentMethod.valueOf(request.paymentMethod()))
                .status(PaymentStatus.valueOf(request.status()))
                .paymentDate(LocalDateTime.now())
                .referenceNumber(request.referenceNumber())
                .active(true)
                .build();
        return paymentMapper.toResponse(paymentRepository.save(payment));
    }

    @Transactional
    public void delete(Long id) {
        var payment = findPayment(id);
        payment.setActive(false);
        paymentRepository.save(payment);
    }

    private Payment findPayment(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado: " + id));
    }
}
