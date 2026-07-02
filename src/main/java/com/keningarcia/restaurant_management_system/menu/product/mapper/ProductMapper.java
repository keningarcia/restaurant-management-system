package com.keningarcia.restaurant_management_system.menu.product.mapper;

import com.keningarcia.restaurant_management_system.menu.product.dto.ProductResponse;
import com.keningarcia.restaurant_management_system.menu.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "status", expression = "java(product.getStatus().name())")
    ProductResponse toResponse(Product product);
}
