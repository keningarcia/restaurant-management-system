package com.keningarcia.restaurant_management_system.mapper;

import com.keningarcia.restaurant_management_system.dto.EmployeeResponse;
import com.keningarcia.restaurant_management_system.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "position", expression = "java(employee.getPosition().name())")
    EmployeeResponse toResponse(Employee employee);
}
