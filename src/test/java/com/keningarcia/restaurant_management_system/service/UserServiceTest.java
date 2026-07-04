package com.keningarcia.restaurant_management_system.service;

import com.keningarcia.restaurant_management_system.exceptions.DuplicateResourceException;
import com.keningarcia.restaurant_management_system.exceptions.ResourceNotFoundException;
import com.keningarcia.restaurant_management_system.entity.Role;
import com.keningarcia.restaurant_management_system.enums.RoleEnum;
import com.keningarcia.restaurant_management_system.repository.RoleRepository;
import com.keningarcia.restaurant_management_system.dto.UserRequest;
import com.keningarcia.restaurant_management_system.dto.UserResponse;
import com.keningarcia.restaurant_management_system.entity.User;
import com.keningarcia.restaurant_management_system.enums.UserStatus;
import com.keningarcia.restaurant_management_system.mapper.UserMapper;
import com.keningarcia.restaurant_management_system.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private RoleRepository roleRepository;
    @Mock private UserMapper userMapper;
    @Mock private PasswordEncoder passwordEncoder;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, roleRepository, userMapper, passwordEncoder);
    }

    @Test
    void findById_WhenNotExists_ThrowsException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.findById(99L));
    }

    @Test
    void create_WhenUsernameExists_ThrowsException() {
        var request = new UserRequest("existing", "test@test.com", "pass123", "User", Set.of(1L));
        when(userRepository.existsByUsername("existing")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> userService.create(request));
    }

    @Test
    void create_WhenValid_CreatesUser() {
        var request = new UserRequest("newuser", "new@test.com", "pass123", "New User", Set.of(1L));
        var role = Role.builder().id(1L).name(RoleEnum.WAITER).build();

        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.existsByEmail("new@test.com")).thenReturn(false);
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(passwordEncoder.encode("pass123")).thenReturn("encoded");

        var savedUser = User.builder().id(1L).username("newuser").email("new@test.com")
                .fullName("New User").password("encoded").roles(Set.of(role))
                .status(UserStatus.ACTIVE).active(true).build();
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        var expectedResponse = new UserResponse(1L, "newuser", "new@test.com",
                "New User", "ACTIVE", Set.of("WAITER"), true, null);
        when(userMapper.toResponse(savedUser)).thenReturn(expectedResponse);

        UserResponse response = userService.create(request);

        assertNotNull(response);
        assertEquals("newuser", response.username());
        assertEquals("New User", response.fullName());
    }

    @Test
    void delete_SoftDeletesUser() {
        var user = User.builder().id(1L).username("user").active(true).build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        userService.delete(1L);

        assertFalse(user.getActive());
    }
}
