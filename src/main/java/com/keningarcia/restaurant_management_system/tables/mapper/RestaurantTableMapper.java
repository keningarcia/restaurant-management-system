package com.keningarcia.restaurant_management_system.tables.mapper;

import com.keningarcia.restaurant_management_system.tables.dto.RestaurantTableResponse;
import com.keningarcia.restaurant_management_system.tables.entity.RestaurantTable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RestaurantTableMapper {

    @Mapping(target = "status", expression = "java(table.getStatus().name())")
    RestaurantTableResponse toResponse(RestaurantTable table);
}
