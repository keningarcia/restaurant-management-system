package com.keningarcia.restaurant_management_system.tables.entity;

import com.keningarcia.restaurant_management_system.common.BaseEntity;
import com.keningarcia.restaurant_management_system.tables.enums.TableStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "restaurant_tables")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantTable extends BaseEntity {

    @Column(nullable = false, unique = true, length = 10)
    private String tableNumber;

    @Column(nullable = false)
    private Integer capacity;

    @Column(length = 50)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TableStatus status;
}
