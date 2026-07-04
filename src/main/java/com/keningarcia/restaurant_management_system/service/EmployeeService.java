package com.keningarcia.restaurant_management_system.service;

import com.keningarcia.restaurant_management_system.dto.EmployeeRequest;
import com.keningarcia.restaurant_management_system.dto.EmployeeResponse;
import com.keningarcia.restaurant_management_system.entity.Employee;
import com.keningarcia.restaurant_management_system.enums.EmployeePosition;
import com.keningarcia.restaurant_management_system.mapper.EmployeeMapper;
import com.keningarcia.restaurant_management_system.repository.EmployeeRepository;
import com.keningarcia.restaurant_management_system.exceptions.ResourceNotFoundException;
import com.keningarcia.restaurant_management_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final EmployeeMapper employeeMapper;

    @Transactional(readOnly = true)
    public Page<EmployeeResponse> findAll(Pageable pageable) {
        return employeeRepository.findAll(pageable).map(employeeMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public EmployeeResponse findById(Long id) {
        return employeeMapper.toResponse(findEmployee(id));
    }

    @Transactional
    public EmployeeResponse create(EmployeeRequest request) {
        var user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + request.userId()));

        var employee = Employee.builder()
                .user(user)
                .phone(request.phone())
                .address(request.address())
                .position(EmployeePosition.valueOf(request.position()))
                .hireDate(request.hireDate())
                .salary(request.salary())
                .documentNumber(request.documentNumber())
                .active(true)
                .build();

        return employeeMapper.toResponse(employeeRepository.save(employee));
    }

    @Transactional
    public EmployeeResponse update(Long id, EmployeeRequest request) {
        var employee = findEmployee(id);
        employee.setPhone(request.phone());
        employee.setAddress(request.address());
        employee.setPosition(EmployeePosition.valueOf(request.position()));
        employee.setHireDate(request.hireDate());
        employee.setSalary(request.salary());
        employee.setDocumentNumber(request.documentNumber());
        return employeeMapper.toResponse(employeeRepository.save(employee));
    }

    @Transactional
    public void delete(Long id) {
        var employee = findEmployee(id);
        employee.setActive(false);
        employeeRepository.save(employee);
    }

    private Employee findEmployee(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado: " + id));
    }
}
