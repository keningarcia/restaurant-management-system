package com.keningarcia.restaurant_management_system.purchases.specification;

import com.keningarcia.restaurant_management_system.purchases.entity.Purchase;
import com.keningarcia.restaurant_management_system.purchases.enums.PurchaseStatus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PurchaseSpecification {

    public static Specification<Purchase> withFilters(Long supplierId, String status, Boolean active) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (supplierId != null) {
                predicates.add(cb.equal(root.get("supplier").get("id"), supplierId));
            }
            if (status != null && !status.isBlank()) {
                predicates.add(cb.equal(root.get("status"), PurchaseStatus.valueOf(status)));
            }
            if (active != null) {
                predicates.add(cb.equal(root.get("active"), active));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
