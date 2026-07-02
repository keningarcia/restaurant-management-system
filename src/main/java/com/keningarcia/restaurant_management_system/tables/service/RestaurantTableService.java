package com.keningarcia.restaurant_management_system.tables.service;

import com.keningarcia.restaurant_management_system.exceptions.DuplicateResourceException;
import com.keningarcia.restaurant_management_system.exceptions.ResourceNotFoundException;
import com.keningarcia.restaurant_management_system.tables.dto.RestaurantTableRequest;
import com.keningarcia.restaurant_management_system.tables.dto.RestaurantTableResponse;
import com.keningarcia.restaurant_management_system.tables.entity.RestaurantTable;
import com.keningarcia.restaurant_management_system.tables.enums.TableStatus;
import com.keningarcia.restaurant_management_system.tables.mapper.RestaurantTableMapper;
import com.keningarcia.restaurant_management_system.tables.repository.RestaurantTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RestaurantTableService {

    private final RestaurantTableRepository tableRepository;
    private final RestaurantTableMapper tableMapper;

    @Transactional(readOnly = true)
    public Page<RestaurantTableResponse> findAll(Pageable pageable) {
        return tableRepository.findAll(pageable).map(tableMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public RestaurantTableResponse findById(Long id) {
        return tableMapper.toResponse(findTable(id));
    }

    @Transactional
    public RestaurantTableResponse create(RestaurantTableRequest request) {
        if (tableRepository.existsByTableNumber(request.tableNumber())) {
            throw new DuplicateResourceException("La mesa ya existe: " + request.tableNumber());
        }

        var table = RestaurantTable.builder()
                .tableNumber(request.tableNumber())
                .capacity(request.capacity())
                .location(request.location())
                .status(TableStatus.valueOf(request.status()))
                .active(true)
                .build();
        return tableMapper.toResponse(tableRepository.save(table));
    }

    @Transactional
    public RestaurantTableResponse update(Long id, RestaurantTableRequest request) {
        var table = findTable(id);
        table.setCapacity(request.capacity());
        table.setLocation(request.location());
        table.setStatus(TableStatus.valueOf(request.status()));
        return tableMapper.toResponse(tableRepository.save(table));
    }

    @Transactional
    public void delete(Long id) {
        var table = findTable(id);
        table.setActive(false);
        tableRepository.save(table);
    }

    private RestaurantTable findTable(Long id) {
        return tableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mesa no encontrada: " + id));
    }
}
