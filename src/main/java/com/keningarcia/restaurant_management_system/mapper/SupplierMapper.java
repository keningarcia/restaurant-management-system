package com.keningarcia.restaurant_management_system.mapper;

import com.keningarcia.restaurant_management_system.dto.SupplierResponse;
import com.keningarcia.restaurant_management_system.entity.Supplier;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    SupplierResponse toResponse(Supplier supplier);
}
