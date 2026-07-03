package com.keningarcia.restaurant_management_system.tables.specification;

import com.keningarcia.restaurant_management_system.tables.entity.RestaurantTable;
import com.keningarcia.restaurant_management_system.tables.enums.TableStatus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class RestaurantTableSpecification {

    public static Specification<RestaurantTable> withFilters(String status, Boolean active) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (status != null && !status.isBlank()) {
                predicates.add(cb.equal(root.get("status"), TableStatus.valueOf(status)));
            }
            if (active != null) {
                predicates.add(cb.equal(root.get("active"), active));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
