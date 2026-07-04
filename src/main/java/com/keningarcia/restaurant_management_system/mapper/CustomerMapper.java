package com.keningarcia.restaurant_management_system.mapper;

import com.keningarcia.restaurant_management_system.dto.CustomerResponse;
import com.keningarcia.restaurant_management_system.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerResponse toResponse(Customer customer);
}
