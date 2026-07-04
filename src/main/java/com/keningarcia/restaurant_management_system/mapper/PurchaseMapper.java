package com.keningarcia.restaurant_management_system.mapper;

import com.keningarcia.restaurant_management_system.dto.PurchaseResponse;
import com.keningarcia.restaurant_management_system.entity.Purchase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PurchaseMapper {

    @Mapping(target = "supplierId", source = "supplier.id")
    @Mapping(target = "supplierName", source = "supplier.name")
    @Mapping(target = "status", expression = "java(purchase.getStatus().name())")
    PurchaseResponse toResponse(Purchase purchase);
}
