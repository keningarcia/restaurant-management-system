package com.keningarcia.restaurant_management_system.service;

import com.keningarcia.restaurant_management_system.dto.CustomerRequest;
import com.keningarcia.restaurant_management_system.dto.CustomerResponse;
import com.keningarcia.restaurant_management_system.entity.Customer;
import com.keningarcia.restaurant_management_system.mapper.CustomerMapper;
import com.keningarcia.restaurant_management_system.repository.CustomerRepository;
import com.keningarcia.restaurant_management_system.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Transactional(readOnly = true)
    public Page<CustomerResponse> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable).map(customerMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public CustomerResponse findById(Long id) {
        return customerMapper.toResponse(findCustomer(id));
    }

    @Transactional
    public CustomerResponse create(CustomerRequest request) {
        var customer = Customer.builder()
                .fullName(request.fullName())
                .phone(request.phone())
                .email(request.email())
                .address(request.address())
                .documentNumber(request.documentNumber())
                .active(true)
                .build();
        return customerMapper.toResponse(customerRepository.save(customer));
    }

    @Transactional
    public CustomerResponse update(Long id, CustomerRequest request) {
        var customer = findCustomer(id);
        customer.setFullName(request.fullName());
        customer.setPhone(request.phone());
        customer.setEmail(request.email());
        customer.setAddress(request.address());
        customer.setDocumentNumber(request.documentNumber());
        return customerMapper.toResponse(customerRepository.save(customer));
    }

    @Transactional
    public void delete(Long id) {
        var customer = findCustomer(id);
        customer.setActive(false);
        customerRepository.save(customer);
    }

    private Customer findCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado: " + id));
    }
}
