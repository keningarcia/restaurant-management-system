package com.keningarcia.restaurant_management_system.auth.service;

import com.keningarcia.restaurant_management_system.auth.dto.AuthResponse;
import com.keningarcia.restaurant_management_system.auth.dto.LoginRequest;
import com.keningarcia.restaurant_management_system.auth.dto.RegisterRequest;
import com.keningarcia.restaurant_management_system.exceptions.DuplicateResourceException;
import com.keningarcia.restaurant_management_system.roles.entity.Role;
import com.keningarcia.restaurant_management_system.roles.enums.RoleEnum;
import com.keningarcia.restaurant_management_system.roles.service.RoleService;
import com.keningarcia.restaurant_management_system.security.JwtTokenProvider;
import com.keningarcia.restaurant_management_system.users.entity.User;
import com.keningarcia.restaurant_management_system.users.enums.UserStatus;
import com.keningarcia.restaurant_management_system.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private RoleService roleService;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private JwtTokenProvider jwtTokenProvider;
    @Mock private AuthenticationManager authenticationManager;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService(userRepository, roleService, passwordEncoder,
                jwtTokenProvider, authenticationManager);
    }

    @Test
    void register_WhenUsernameExists_ThrowsException() {
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        var request = new RegisterRequest("testuser", "test@test.com", "password123", "Test User");

        assertThrows(DuplicateResourceException.class, () -> authService.register(request));
    }

    @Test
    void register_WhenEmailExists_ThrowsException() {
        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@test.com")).thenReturn(true);

        var request = new RegisterRequest("testuser", "test@test.com", "password123", "Test User");

        assertThrows(DuplicateResourceException.class, () -> authService.register(request));
    }

    @Test
    void register_WhenValid_CreatesUserAndReturnsToken() {
        var request = new RegisterRequest("testuser", "test@test.com", "password123", "Test User");
        var waiterRole = Role.builder().id(1L).name(RoleEnum.WAITER).build();

        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@test.com")).thenReturn(false);
        when(roleService.findByName(RoleEnum.WAITER)).thenReturn(waiterRole);
        when(passwordEncoder.encode("password123")).thenReturn("encoded");
        when(jwtTokenProvider.generateToken("testuser")).thenReturn("token");

        var savedUser = User.builder()
                .id(1L).username("testuser").email("test@test.com")
                .fullName("Test User").password("encoded")
                .status(UserStatus.ACTIVE).roles(Set.of(waiterRole)).active(true)
                .build();
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        AuthResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("token", response.token());
        assertEquals("testuser", response.username());
    }

    @Test
    void login_WhenInvalidCredentials_ThrowsException() {
        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        var request = new LoginRequest("testuser", "wrong");

        assertThrows(BadCredentialsException.class, () -> authService.login(request));
    }
}
