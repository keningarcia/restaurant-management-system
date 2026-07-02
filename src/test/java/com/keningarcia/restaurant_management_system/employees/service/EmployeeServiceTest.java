package com.keningarcia.restaurant_management_system.employees.service;

import com.keningarcia.restaurant_management_system.employees.dto.EmployeeRequest;
import com.keningarcia.restaurant_management_system.employees.entity.Employee;
import com.keningarcia.restaurant_management_system.employees.enums.EmployeePosition;
import com.keningarcia.restaurant_management_system.employees.mapper.EmployeeMapper;
import com.keningarcia.restaurant_management_system.employees.repository.EmployeeRepository;
import com.keningarcia.restaurant_management_system.users.entity.User;
import com.keningarcia.restaurant_management_system.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock private EmployeeRepository employeeRepository;
    @Mock private UserRepository userRepository;
    @Mock private EmployeeMapper employeeMapper;

    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        employeeService = new EmployeeService(employeeRepository, userRepository, employeeMapper);
    }

    @Test
    void create_WhenUserExists_CreatesEmployee() {
        var user = User.builder().id(1L).username("waiter1").build();
        var request = new EmployeeRequest(1L, "999888777", "Av. Test 123",
                "WAITER", LocalDate.now(), BigDecimal.valueOf(1500), "DNI123");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(employeeRepository.save(any(Employee.class)))
                .thenReturn(Employee.builder().id(1L).user(user).phone("999888777")
                        .position(EmployeePosition.WAITER).active(true).build());

        employeeService.create(request);

        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void delete_SoftDeletes() {
        var employee = Employee.builder().id(1L).active(true).build();
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee)).thenReturn(employee);

        employeeService.delete(1L);

        assertFalse(employee.getActive());
    }
}
