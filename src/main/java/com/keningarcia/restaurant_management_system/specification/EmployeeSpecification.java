package com.keningarcia.restaurant_management_system.specification;

import com.keningarcia.restaurant_management_system.entity.Employee;
import com.keningarcia.restaurant_management_system.enums.EmployeePosition;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class EmployeeSpecification {

    public static Specification<Employee> withFilters(String position, Boolean active) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (position != null && !position.isBlank()) {
                predicates.add(cb.equal(root.get("position"), EmployeePosition.valueOf(position)));
            }
            if (active != null) {
                predicates.add(cb.equal(root.get("active"), active));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
