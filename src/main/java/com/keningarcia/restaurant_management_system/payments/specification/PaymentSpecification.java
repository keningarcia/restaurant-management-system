package com.keningarcia.restaurant_management_system.payments.specification;

import com.keningarcia.restaurant_management_system.payments.entity.Payment;
import com.keningarcia.restaurant_management_system.payments.enums.PaymentMethod;
import com.keningarcia.restaurant_management_system.payments.enums.PaymentStatus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PaymentSpecification {

    public static Specification<Payment> withFilters(Long orderId, String paymentMethod, String status, Boolean active) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (orderId != null) {
                predicates.add(cb.equal(root.get("order").get("id"), orderId));
            }
            if (paymentMethod != null && !paymentMethod.isBlank()) {
                predicates.add(cb.equal(root.get("paymentMethod"), PaymentMethod.valueOf(paymentMethod)));
            }
            if (status != null && !status.isBlank()) {
                predicates.add(cb.equal(root.get("status"), PaymentStatus.valueOf(status)));
            }
            if (active != null) {
                predicates.add(cb.equal(root.get("active"), active));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
