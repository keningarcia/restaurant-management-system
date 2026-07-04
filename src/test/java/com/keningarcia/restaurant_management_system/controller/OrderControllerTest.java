package com.keningarcia.restaurant_management_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keningarcia.restaurant_management_system.dto.OrderDetailRequest;
import com.keningarcia.restaurant_management_system.dto.OrderRequest;
import com.keningarcia.restaurant_management_system.dto.OrderResponse;
import com.keningarcia.restaurant_management_system.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock private OrderService orderService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new OrderController(orderService)).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void create_ReturnsCreated() throws Exception {
        var detail = new OrderDetailRequest(1L, 2, BigDecimal.valueOf(10), null);
        var request = new OrderRequest(1L, null, 1L, "No onions", List.of(detail));

        var response = new OrderResponse(1L, 1L, "5", null, null, 1L,
                "Waiter", "CREATED", BigDecimal.valueOf(20), BigDecimal.valueOf(3.6),
                BigDecimal.valueOf(23.6), "No onions", List.of(), true, null);

        when(orderService.create(any(OrderRequest.class))).thenReturn(response);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tableNumber").value("5"));
    }

    @Test
    void updateStatus_ReturnsOk() throws Exception {
        var response = new OrderResponse(1L, 1L, "5", null, null, 1L,
                "Waiter", "PREPARING", BigDecimal.valueOf(20), BigDecimal.valueOf(3.6),
                BigDecimal.valueOf(23.6), null, List.of(), true, null);

        when(orderService.updateStatus(1L, "PREPARING")).thenReturn(response);

        mockMvc.perform(patch("/orders/1/status")
                        .param("status", "PREPARING"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PREPARING"));
    }
}
