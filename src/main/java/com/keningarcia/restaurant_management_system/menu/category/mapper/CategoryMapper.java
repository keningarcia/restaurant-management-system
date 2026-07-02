package com.keningarcia.restaurant_management_system.menu.category.mapper;

import com.keningarcia.restaurant_management_system.menu.category.dto.CategoryResponse;
import com.keningarcia.restaurant_management_system.menu.category.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse toResponse(Category category);
}
