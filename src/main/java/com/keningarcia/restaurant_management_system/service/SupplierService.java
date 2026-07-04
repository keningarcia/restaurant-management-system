package com.keningarcia.restaurant_management_system.service;

import com.keningarcia.restaurant_management_system.exceptions.DuplicateResourceException;
import com.keningarcia.restaurant_management_system.exceptions.ResourceNotFoundException;
import com.keningarcia.restaurant_management_system.dto.SupplierRequest;
import com.keningarcia.restaurant_management_system.dto.SupplierResponse;
import com.keningarcia.restaurant_management_system.entity.Supplier;
import com.keningarcia.restaurant_management_system.mapper.SupplierMapper;
import com.keningarcia.restaurant_management_system.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    @Transactional(readOnly = true)
    public Page<SupplierResponse> findAll(Pageable pageable) {
        return supplierRepository.findAll(pageable).map(supplierMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public SupplierResponse findById(Long id) {
        return supplierMapper.toResponse(findSupplier(id));
    }

    @Transactional
    public SupplierResponse create(SupplierRequest request) {
        if (supplierRepository.existsByName(request.name())) {
            throw new DuplicateResourceException("El proveedor ya existe: " + request.name());
        }
        var supplier = Supplier.builder()
                .name(request.name())
                .phone(request.phone())
                .email(request.email())
                .address(request.address())
                .contactPerson(request.contactPerson())
                .documentNumber(request.documentNumber())
                .active(true)
                .build();
        return supplierMapper.toResponse(supplierRepository.save(supplier));
    }

    @Transactional
    public SupplierResponse update(Long id, SupplierRequest request) {
        var supplier = findSupplier(id);
        supplier.setName(request.name());
        supplier.setPhone(request.phone());
        supplier.setEmail(request.email());
        supplier.setAddress(request.address());
        supplier.setContactPerson(request.contactPerson());
        supplier.setDocumentNumber(request.documentNumber());
        return supplierMapper.toResponse(supplierRepository.save(supplier));
    }

    @Transactional
    public void delete(Long id) {
        var supplier = findSupplier(id);
        supplier.setActive(false);
        supplierRepository.save(supplier);
    }

    private Supplier findSupplier(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado: " + id));
    }
}
