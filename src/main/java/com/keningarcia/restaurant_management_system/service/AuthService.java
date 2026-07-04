package com.keningarcia.restaurant_management_system.service;

import com.keningarcia.restaurant_management_system.dto.AuthResponse;
import com.keningarcia.restaurant_management_system.dto.LoginRequest;
import com.keningarcia.restaurant_management_system.dto.RegisterRequest;
import com.keningarcia.restaurant_management_system.exceptions.DuplicateResourceException;
import com.keningarcia.restaurant_management_system.enums.RoleEnum;
import com.keningarcia.restaurant_management_system.service.RoleService;
import com.keningarcia.restaurant_management_system.security.JwtTokenProvider;
import com.keningarcia.restaurant_management_system.entity.User;
import com.keningarcia.restaurant_management_system.enums.UserStatus;
import com.keningarcia.restaurant_management_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new DuplicateResourceException("El username ya esta en uso");
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("El email ya esta en uso");
        }

        var waiterRole = roleService.findByName(RoleEnum.WAITER);

        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .fullName(request.fullName())
                .status(UserStatus.ACTIVE)
                .roles(Set.of(waiterRole))
                .active(true)
                .build();

        user = userRepository.save(user);

        String token = jwtTokenProvider.generateToken(user.getUsername(), user.getId());

        return new AuthResponse(token, "Bearer", user.getId(), user.getUsername(),
                user.getEmail(), user.getFullName());
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String token = jwtTokenProvider.generateToken(user.getUsername(), user.getId());

        return new AuthResponse(token, "Bearer", user.getId(), user.getUsername(),
                user.getEmail(), user.getFullName());
    }
}
