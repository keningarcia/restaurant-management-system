package com.keningarcia.restaurant_management_system.service;

import com.keningarcia.restaurant_management_system.exceptions.DuplicateResourceException;
import com.keningarcia.restaurant_management_system.exceptions.ResourceNotFoundException;
import com.keningarcia.restaurant_management_system.repository.CategoryRepository;
import com.keningarcia.restaurant_management_system.dto.ProductRequest;
import com.keningarcia.restaurant_management_system.dto.ProductResponse;
import com.keningarcia.restaurant_management_system.entity.Product;
import com.keningarcia.restaurant_management_system.enums.ProductStatus;
import com.keningarcia.restaurant_management_system.mapper.ProductMapper;
import com.keningarcia.restaurant_management_system.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    public Page<ProductResponse> findAll(Pageable pageable) {
        return productRepository.findAll(pageable).map(productMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        return productMapper.toResponse(findProduct(id));
    }

    @Transactional
    public ProductResponse create(ProductRequest request) {
        if (productRepository.existsByName(request.name())) {
            throw new DuplicateResourceException("El producto ya existe: " + request.name());
        }

        var category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada: " + request.categoryId()));

        var product = Product.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .category(category)
                .status(ProductStatus.valueOf(request.status()))
                .imageUrl(request.imageUrl())
                .active(true)
                .build();
        return productMapper.toResponse(productRepository.save(product));
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        var product = findProduct(id);
        var category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada: " + request.categoryId()));

        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setCategory(category);
        product.setStatus(ProductStatus.valueOf(request.status()));
        product.setImageUrl(request.imageUrl());
        return productMapper.toResponse(productRepository.save(product));
    }

    @Transactional
    public void delete(Long id) {
        var product = findProduct(id);
        product.setActive(false);
        productRepository.save(product);
    }

    private Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + id));
    }
}
