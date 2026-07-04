package com.keningarcia.restaurant_management_system.mapper;

import com.keningarcia.restaurant_management_system.dto.RestaurantTableResponse;
import com.keningarcia.restaurant_management_system.entity.RestaurantTable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RestaurantTableMapper {

    @Mapping(target = "status", expression = "java(table.getStatus().name())")
    RestaurantTableResponse toResponse(RestaurantTable table);
}
