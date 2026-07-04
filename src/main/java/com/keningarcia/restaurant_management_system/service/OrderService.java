package com.keningarcia.restaurant_management_system.service;

import com.keningarcia.restaurant_management_system.repository.CustomerRepository;
import com.keningarcia.restaurant_management_system.repository.EmployeeRepository;
import com.keningarcia.restaurant_management_system.exceptions.ResourceNotFoundException;
import com.keningarcia.restaurant_management_system.repository.ProductRepository;
import com.keningarcia.restaurant_management_system.dto.OrderDetailRequest;
import com.keningarcia.restaurant_management_system.dto.OrderRequest;
import com.keningarcia.restaurant_management_system.dto.OrderResponse;
import com.keningarcia.restaurant_management_system.entity.Order;
import com.keningarcia.restaurant_management_system.entity.OrderDetail;
import com.keningarcia.restaurant_management_system.enums.OrderStatus;
import com.keningarcia.restaurant_management_system.mapper.OrderMapper;
import com.keningarcia.restaurant_management_system.repository.OrderDetailRepository;
import com.keningarcia.restaurant_management_system.repository.OrderRepository;
import com.keningarcia.restaurant_management_system.specification.OrderSpecification;
import com.keningarcia.restaurant_management_system.repository.RestaurantTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final RestaurantTableRepository tableRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    private static final BigDecimal TAX_RATE = new BigDecimal("0.18");

    @Transactional(readOnly = true)
    public Page<OrderResponse> findAll(Long tableId, String status, Long employeeId,
                                       Boolean active, Pageable pageable) {
        var spec = OrderSpecification.withFilters(tableId, status, employeeId, active);
        return orderRepository.findAll(spec, pageable).map(orderMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public OrderResponse findById(Long id) {
        return orderMapper.toResponse(findOrder(id));
    }

    @Transactional
    public OrderResponse create(OrderRequest request) {
        var table = tableRepository.findById(request.tableId())
                .orElseThrow(() -> new ResourceNotFoundException("Mesa no encontrada: " + request.tableId()));

        var employee = employeeRepository.findById(request.employeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado: " + request.employeeId()));

        var order = Order.builder()
                .table(table)
                .employee(employee)
                .status(OrderStatus.CREATED)
                .notes(request.notes())
                .subtotal(BigDecimal.ZERO)
                .tax(BigDecimal.ZERO)
                .total(BigDecimal.ZERO)
                .active(true)
                .details(new ArrayList<>())
                .build();

        if (request.customerId() != null) {
            var customer = customerRepository.findById(request.customerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado: " + request.customerId()));
            order.setCustomer(customer);
        }

        order = orderRepository.save(order);
        var finalOrder = order;

        var details = request.details().stream()
                .map(detailRequest -> createDetail(detailRequest, finalOrder))
                .toList();

        finalOrder.setDetails(details);
        recalculateTotals(finalOrder);
        finalOrder.setStatus(OrderStatus.SENT_TO_KITCHEN);

        return orderMapper.toResponse(orderRepository.save(finalOrder));
    }

    @Transactional
    public OrderResponse updateStatus(Long id, String status) {
        var order = findOrder(id);
        order.setStatus(OrderStatus.valueOf(status));
        return orderMapper.toResponse(orderRepository.save(order));
    }

    @Transactional
    public OrderResponse update(Long id, OrderRequest request) {
        var order = findOrder(id);

        var table = tableRepository.findById(request.tableId())
                .orElseThrow(() -> new ResourceNotFoundException("Mesa no encontrada: " + request.tableId()));

        var employee = employeeRepository.findById(request.employeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado: " + request.employeeId()));

        order.setTable(table);
        order.setEmployee(employee);
        order.setNotes(request.notes());

        if (request.customerId() != null) {
            var customer = customerRepository.findById(request.customerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado: " + request.customerId()));
            order.setCustomer(customer);
        } else {
            order.setCustomer(null);
        }

        order.getDetails().clear();

        var details = request.details().stream()
                .map(detailRequest -> createDetail(detailRequest, order))
                .toList();

        order.getDetails().addAll(details);
        recalculateTotals(order);

        return orderMapper.toResponse(orderRepository.save(order));
    }

    @Transactional
    public void delete(Long id) {
        var order = findOrder(id);
        order.setActive(false);
        orderRepository.save(order);
    }

    private OrderDetail createDetail(OrderDetailRequest request, Order order) {
        var product = productRepository.findById(request.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + request.productId()));

        var subtotal = request.unitPrice().multiply(BigDecimal.valueOf(request.quantity()));

        return OrderDetail.builder()
                .order(order)
                .product(product)
                .quantity(request.quantity())
                .unitPrice(request.unitPrice())
                .subtotal(subtotal)
                .notes(request.notes())
                .active(true)
                .build();
    }

    private void recalculateTotals(Order order) {
        var subtotal = order.getDetails().stream()
                .map(OrderDetail::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        var tax = subtotal.multiply(TAX_RATE).setScale(2, RoundingMode.HALF_UP);
        var total = subtotal.add(tax);

        order.setSubtotal(subtotal);
        order.setTax(tax);
        order.setTotal(total);
    }

    private Order findOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado: " + id));
    }
}
