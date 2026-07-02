package com.keningarcia.restaurant_management_system.customers.mapper;

import com.keningarcia.restaurant_management_system.customers.dto.CustomerResponse;
import com.keningarcia.restaurant_management_system.customers.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerResponse toResponse(Customer customer);
}
