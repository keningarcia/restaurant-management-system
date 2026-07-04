package com.keningarcia.restaurant_management_system.repository;

import com.keningarcia.restaurant_management_system.entity.Role;
import com.keningarcia.restaurant_management_system.enums.RoleEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleRepositoryTest {

    @Mock private RoleRepository roleRepository;

    @Test
    void findByName_WhenExists_ReturnsRole() {
        var role = Role.builder().id(1L).name(RoleEnum.WAITER).description("Waiter").active(true).build();
        when(roleRepository.findByName(RoleEnum.WAITER)).thenReturn(Optional.of(role));

        Optional<Role> found = roleRepository.findByName(RoleEnum.WAITER);

        assertTrue(found.isPresent());
        assertEquals(RoleEnum.WAITER, found.get().getName());
    }

    @Test
    void findByName_WhenNotExists_ReturnsEmpty() {
        when(roleRepository.findByName(RoleEnum.ADMIN)).thenReturn(Optional.empty());

        Optional<Role> found = roleRepository.findByName(RoleEnum.ADMIN);

        assertFalse(found.isPresent());
    }

    @Test
    void existsByName_WhenExists_ReturnsTrue() {
        when(roleRepository.existsByName(RoleEnum.CHEF)).thenReturn(true);

        assertTrue(roleRepository.existsByName(RoleEnum.CHEF));
    }
}
