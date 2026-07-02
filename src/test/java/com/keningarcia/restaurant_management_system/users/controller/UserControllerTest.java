package com.keningarcia.restaurant_management_system.users.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keningarcia.restaurant_management_system.users.dto.UserRequest;
import com.keningarcia.restaurant_management_system.users.dto.UserResponse;
import com.keningarcia.restaurant_management_system.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock private UserService userService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService))
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void findById_ReturnsUser() throws Exception {
        var roles = new HashSet<String>();
        roles.add("ADMIN");
        var response = new UserResponse(1L, "user1", "user1@test.com",
                "User One", "ACTIVE", roles, true, null);
        when(userService.findById(1L)).thenReturn(response);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user1"));
    }

    @Test
    void create_ReturnsCreated() throws Exception {
        var roleIds = new HashSet<Long>();
        roleIds.add(1L);
        var request = new UserRequest("newuser", "new@test.com", "pass123", "New User", roleIds);
        var roles = new HashSet<String>();
        roles.add("WAITER");
        var response = new UserResponse(1L, "newuser", "new@test.com",
                "New User", "ACTIVE", roles, true, null);
        when(userService.create(any(UserRequest.class))).thenReturn(response);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("newuser"));
    }
}
