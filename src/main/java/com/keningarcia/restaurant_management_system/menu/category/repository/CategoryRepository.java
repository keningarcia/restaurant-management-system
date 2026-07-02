package com.keningarcia.restaurant_management_system.menu.category.repository;

import com.keningarcia.restaurant_management_system.menu.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
}
