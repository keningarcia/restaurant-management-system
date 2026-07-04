package com.keningarcia.restaurant_management_system.service;

import com.keningarcia.restaurant_management_system.exceptions.ResourceNotFoundException;
import com.keningarcia.restaurant_management_system.entity.Role;
import com.keningarcia.restaurant_management_system.enums.RoleEnum;
import com.keningarcia.restaurant_management_system.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Role findByName(RoleEnum name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado: " + name));
    }
}
