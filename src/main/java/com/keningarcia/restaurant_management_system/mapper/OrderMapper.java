package com.keningarcia.restaurant_management_system.mapper;

import com.keningarcia.restaurant_management_system.dto.OrderDetailResponse;
import com.keningarcia.restaurant_management_system.dto.OrderResponse;
import com.keningarcia.restaurant_management_system.entity.Order;
import com.keningarcia.restaurant_management_system.entity.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "tableId", source = "table.id")
    @Mapping(target = "tableNumber", source = "table.tableNumber")
    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "customerName", source = "customer.fullName")
    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "employeeName", source = "employee.user.fullName")
    @Mapping(target = "status", expression = "java(order.getStatus().name())")
    @Mapping(target = "details", expression = "java(mapDetails(order.getDetails()))")
    OrderResponse toResponse(Order order);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    OrderDetailResponse toDetailResponse(OrderDetail detail);

    List<OrderDetailResponse> mapDetails(List<OrderDetail> details);
}
