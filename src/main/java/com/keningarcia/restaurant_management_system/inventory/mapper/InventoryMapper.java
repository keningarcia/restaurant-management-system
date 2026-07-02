package com.keningarcia.restaurant_management_system.inventory.mapper;

import com.keningarcia.restaurant_management_system.inventory.dto.InventoryResponse;
import com.keningarcia.restaurant_management_system.inventory.entity.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "supplierId", source = "supplier.id")
    @Mapping(target = "supplierName", source = "supplier.name")
    InventoryResponse toResponse(Inventory inventory);
}
