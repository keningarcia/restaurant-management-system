package com.keningarcia.restaurant_management_system.service;

import com.keningarcia.restaurant_management_system.exceptions.ResourceNotFoundException;
import com.keningarcia.restaurant_management_system.dto.PurchaseRequest;
import com.keningarcia.restaurant_management_system.dto.PurchaseResponse;
import com.keningarcia.restaurant_management_system.entity.Purchase;
import com.keningarcia.restaurant_management_system.enums.PurchaseStatus;
import com.keningarcia.restaurant_management_system.mapper.PurchaseMapper;
import com.keningarcia.restaurant_management_system.repository.PurchaseRepository;
import com.keningarcia.restaurant_management_system.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final SupplierRepository supplierRepository;
    private final PurchaseMapper purchaseMapper;

    @Transactional(readOnly = true)
    public Page<PurchaseResponse> findAll(Pageable pageable) {
        return purchaseRepository.findAll(pageable).map(purchaseMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public PurchaseResponse findById(Long id) {
        return purchaseMapper.toResponse(findPurchase(id));
    }

    @Transactional
    public PurchaseResponse create(PurchaseRequest request) {
        var supplier = supplierRepository.findById(request.supplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado: " + request.supplierId()));

        var purchase = Purchase.builder()
                .supplier(supplier)
                .purchaseDate(request.purchaseDate())
                .totalAmount(request.totalAmount())
                .status(PurchaseStatus.valueOf(request.status()))
                .notes(request.notes())
                .invoiceNumber(request.invoiceNumber())
                .active(true)
                .build();
        return purchaseMapper.toResponse(purchaseRepository.save(purchase));
    }

    @Transactional
    public PurchaseResponse update(Long id, PurchaseRequest request) {
        var purchase = findPurchase(id);
        var supplier = supplierRepository.findById(request.supplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado: " + request.supplierId()));

        purchase.setSupplier(supplier);
        purchase.setPurchaseDate(request.purchaseDate());
        purchase.setTotalAmount(request.totalAmount());
        purchase.setStatus(PurchaseStatus.valueOf(request.status()));
        purchase.setNotes(request.notes());
        purchase.setInvoiceNumber(request.invoiceNumber());
        return purchaseMapper.toResponse(purchaseRepository.save(purchase));
    }

    @Transactional
    public void delete(Long id) {
        var purchase = findPurchase(id);
        purchase.setActive(false);
        purchaseRepository.save(purchase);
    }

    private Purchase findPurchase(Long id) {
        return purchaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compra no encontrada: " + id));
    }
}
