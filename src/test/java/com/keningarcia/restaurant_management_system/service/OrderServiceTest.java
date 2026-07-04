package com.keningarcia.restaurant_management_system.service;

import com.keningarcia.restaurant_management_system.repository.CustomerRepository;
import com.keningarcia.restaurant_management_system.entity.Employee;
import com.keningarcia.restaurant_management_system.enums.EmployeePosition;
import com.keningarcia.restaurant_management_system.repository.EmployeeRepository;
import com.keningarcia.restaurant_management_system.entity.Product;
import com.keningarcia.restaurant_management_system.repository.ProductRepository;
import com.keningarcia.restaurant_management_system.dto.OrderDetailRequest;
import com.keningarcia.restaurant_management_system.dto.OrderRequest;
import com.keningarcia.restaurant_management_system.dto.OrderResponse;
import com.keningarcia.restaurant_management_system.entity.Order;
import com.keningarcia.restaurant_management_system.enums.OrderStatus;
import com.keningarcia.restaurant_management_system.mapper.OrderMapper;
import com.keningarcia.restaurant_management_system.repository.OrderDetailRepository;
import com.keningarcia.restaurant_management_system.repository.OrderRepository;
import com.keningarcia.restaurant_management_system.entity.RestaurantTable;
import com.keningarcia.restaurant_management_system.enums.TableStatus;
import com.keningarcia.restaurant_management_system.repository.RestaurantTableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock private OrderRepository orderRepository;
    @Mock private OrderDetailRepository orderDetailRepository;
    @Mock private RestaurantTableRepository tableRepository;
    @Mock private CustomerRepository customerRepository;
    @Mock private EmployeeRepository employeeRepository;
    @Mock private ProductRepository productRepository;
    @Mock private OrderMapper orderMapper;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService(orderRepository, orderDetailRepository, tableRepository,
                customerRepository, employeeRepository, productRepository, orderMapper);
    }

    @Test
    void create_WhenTableExists_CreatesOrder() {
        var table = RestaurantTable.builder().id(1L).tableNumber("5").capacity(4)
                .status(TableStatus.AVAILABLE).build();
        var employee = Employee.builder().id(1L).position(EmployeePosition.WAITER)
                .hireDate(LocalDate.now()).salary(BigDecimal.valueOf(1000)).build();
        var product = Product.builder().id(1L).name("Burger").price(BigDecimal.valueOf(10)).build();

        var detailRequest = new OrderDetailRequest(1L, 2, BigDecimal.valueOf(10), null);
        var request = new OrderRequest(1L, null, 1L, "No onions", List.of(detailRequest));

        when(tableRepository.findById(1L)).thenReturn(Optional.of(table));
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        var savedOrder = Order.builder().id(1L).table(table).employee(employee)
                .status(OrderStatus.CREATED).subtotal(BigDecimal.ZERO).build();

        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            var o = invocation.getArgument(0, Order.class);
            o.setId(1L);
            return o;
        });

        OrderResponse mockResponse = new OrderResponse(1L, 1L, "5", null, null, 1L,
                "Test", "CREATED", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, null,
                List.of(), true, null);
        when(orderMapper.toResponse(any(Order.class))).thenReturn(mockResponse);

        var result = orderService.create(request);

        assertNotNull(result);
    }

    @Test
    void updateStatus_ChangesStatus() {
        var order = Order.builder().id(1L).status(OrderStatus.CREATED).build();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        orderService.updateStatus(1L, "PREPARING");

        assertEquals(OrderStatus.PREPARING, order.getStatus());
    }
}
