package com.keningarcia.restaurant_management_system.purchases.mapper;

import com.keningarcia.restaurant_management_system.purchases.dto.PurchaseResponse;
import com.keningarcia.restaurant_management_system.purchases.entity.Purchase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PurchaseMapper {

    @Mapping(target = "supplierId", source = "supplier.id")
    @Mapping(target = "supplierName", source = "supplier.name")
    @Mapping(target = "status", expression = "java(purchase.getStatus().name())")
    PurchaseResponse toResponse(Purchase purchase);
}
