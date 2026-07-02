package com.keningarcia.restaurant_management_system.suppliers.mapper;

import com.keningarcia.restaurant_management_system.suppliers.dto.SupplierResponse;
import com.keningarcia.restaurant_management_system.suppliers.entity.Supplier;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    SupplierResponse toResponse(Supplier supplier);
}
