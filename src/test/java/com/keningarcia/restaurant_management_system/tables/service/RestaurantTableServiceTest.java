package com.keningarcia.restaurant_management_system.tables.service;

import com.keningarcia.restaurant_management_system.exceptions.DuplicateResourceException;
import com.keningarcia.restaurant_management_system.tables.dto.RestaurantTableRequest;
import com.keningarcia.restaurant_management_system.tables.entity.RestaurantTable;
import com.keningarcia.restaurant_management_system.tables.enums.TableStatus;
import com.keningarcia.restaurant_management_system.tables.mapper.RestaurantTableMapper;
import com.keningarcia.restaurant_management_system.tables.repository.RestaurantTableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTableServiceTest {

    @Mock private RestaurantTableRepository tableRepository;
    @Mock private RestaurantTableMapper tableMapper;

    private RestaurantTableService tableService;

    @BeforeEach
    void setUp() {
        tableService = new RestaurantTableService(tableRepository, tableMapper);
    }

    @Test
    void create_WhenTableNumberExists_ThrowsException() {
        var request = new RestaurantTableRequest("1", 4, "Main Hall", "AVAILABLE");
        when(tableRepository.existsByTableNumber("1")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> tableService.create(request));
    }

    @Test
    void create_WhenValid_CreatesTable() {
        var request = new RestaurantTableRequest("10", 6, "Terrace", "AVAILABLE");
        when(tableRepository.existsByTableNumber("10")).thenReturn(false);

        var table = RestaurantTable.builder().id(1L).tableNumber("10").capacity(6)
                .location("Terrace").status(TableStatus.AVAILABLE).active(true).build();
        when(tableRepository.save(any(RestaurantTable.class))).thenReturn(table);

        tableService.create(request);

        verify(tableRepository).save(any(RestaurantTable.class));
    }
}
