package com.keningarcia.restaurant_management_system.entity;

import com.keningarcia.restaurant_management_system.common.BaseEntity;
import com.keningarcia.restaurant_management_system.enums.EmployeePosition;
import com.keningarcia.restaurant_management_system.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(nullable = false, length = 150)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EmployeePosition position;

    @Column(nullable = false)
    private LocalDate hireDate;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal salary;

    @Column(length = 20)
    private String documentNumber;
}
