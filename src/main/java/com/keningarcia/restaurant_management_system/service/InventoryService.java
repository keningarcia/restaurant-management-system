package com.keningarcia.restaurant_management_system.service;

import com.keningarcia.restaurant_management_system.exceptions.ResourceNotFoundException;
import com.keningarcia.restaurant_management_system.dto.InventoryRequest;
import com.keningarcia.restaurant_management_system.dto.InventoryResponse;
import com.keningarcia.restaurant_management_system.entity.Inventory;
import com.keningarcia.restaurant_management_system.mapper.InventoryMapper;
import com.keningarcia.restaurant_management_system.repository.InventoryRepository;
import com.keningarcia.restaurant_management_system.repository.ProductRepository;
import com.keningarcia.restaurant_management_system.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final InventoryMapper inventoryMapper;

    @Transactional(readOnly = true)
    public Page<InventoryResponse> findAll(Pageable pageable) {
        return inventoryRepository.findAll(pageable).map(inventoryMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public InventoryResponse findById(Long id) {
        return inventoryMapper.toResponse(findInventory(id));
    }

    @Transactional
    public InventoryResponse create(InventoryRequest request) {
        var product = productRepository.findById(request.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + request.productId()));

        var inventory = Inventory.builder()
                .product(product)
                .quantity(request.quantity())
                .unit(request.unit())
                .unitPrice(request.unitPrice())
                .expirationDate(request.expirationDate())
                .minimumStock(request.minimumStock())
                .active(true)
                .build();

        if (request.supplierId() != null) {
            var supplier = supplierRepository.findById(request.supplierId())
                    .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado: " + request.supplierId()));
            inventory.setSupplier(supplier);
        }

        return inventoryMapper.toResponse(inventoryRepository.save(inventory));
    }

    @Transactional
    public InventoryResponse update(Long id, InventoryRequest request) {
        var inventory = findInventory(id);
        var product = productRepository.findById(request.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + request.productId()));

        inventory.setProduct(product);
        inventory.setQuantity(request.quantity());
        inventory.setUnit(request.unit());
        inventory.setUnitPrice(request.unitPrice());
        inventory.setExpirationDate(request.expirationDate());
        inventory.setMinimumStock(request.minimumStock());

        if (request.supplierId() != null) {
            var supplier = supplierRepository.findById(request.supplierId())
                    .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado: " + request.supplierId()));
            inventory.setSupplier(supplier);
        } else {
            inventory.setSupplier(null);
        }

        return inventoryMapper.toResponse(inventoryRepository.save(inventory));
    }

    @Transactional
    public void delete(Long id) {
        var inventory = findInventory(id);
        inventory.setActive(false);
        inventoryRepository.save(inventory);
    }

    private Inventory findInventory(Long id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventario no encontrado: " + id));
    }
}
