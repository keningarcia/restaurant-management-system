package com.keningarcia.restaurant_management_system.inventory.service;

import com.keningarcia.restaurant_management_system.inventory.dto.InventoryRequest;
import com.keningarcia.restaurant_management_system.inventory.dto.InventoryResponse;
import com.keningarcia.restaurant_management_system.inventory.entity.Inventory;
import com.keningarcia.restaurant_management_system.inventory.mapper.InventoryMapper;
import com.keningarcia.restaurant_management_system.inventory.repository.InventoryRepository;
import com.keningarcia.restaurant_management_system.menu.product.entity.Product;
import com.keningarcia.restaurant_management_system.menu.product.repository.ProductRepository;
import com.keningarcia.restaurant_management_system.suppliers.repository.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock private InventoryRepository inventoryRepository;
    @Mock private ProductRepository productRepository;
    @Mock private SupplierRepository supplierRepository;
    @Mock private InventoryMapper inventoryMapper;

    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        inventoryService = new InventoryService(inventoryRepository, productRepository,
                supplierRepository, inventoryMapper);
    }

    @Test
    void create_WhenProductExists_CreatesInventory() {
        var product = Product.builder().id(1L).name("Tomato").build();
        var request = new InventoryRequest(1L, 100, "kg", null, null, null, 10);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(inventoryRepository.save(any(Inventory.class)))
                .thenReturn(Inventory.builder().id(1L).product(product).quantity(100).unit("kg")
                        .minimumStock(10).active(true).build());

        var response = new InventoryResponse(1L, 1L, "Tomato", 100, "kg", null,
                null, null, null, 10, true, null);
        when(inventoryMapper.toResponse(any(Inventory.class))).thenReturn(response);

        var result = inventoryService.create(request);

        assertNotNull(result);
        assertEquals(100, result.quantity());
        assertEquals("Tomato", result.productName());
    }

    @Test
    void delete_SoftDeletes() {
        var inventory = Inventory.builder().id(1L).active(true).build();
        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(inventory));
        when(inventoryRepository.save(inventory)).thenReturn(inventory);

        inventoryService.delete(1L);

        assertFalse(inventory.getActive());
    }
}
