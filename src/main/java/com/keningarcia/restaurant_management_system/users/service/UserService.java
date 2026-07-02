package com.keningarcia.restaurant_management_system.users.service;

import com.keningarcia.restaurant_management_system.exceptions.DuplicateResourceException;
import com.keningarcia.restaurant_management_system.exceptions.ResourceNotFoundException;
import com.keningarcia.restaurant_management_system.roles.entity.Role;
import com.keningarcia.restaurant_management_system.roles.repository.RoleRepository;
import com.keningarcia.restaurant_management_system.users.dto.UserRequest;
import com.keningarcia.restaurant_management_system.users.dto.UserResponse;
import com.keningarcia.restaurant_management_system.users.entity.User;
import com.keningarcia.restaurant_management_system.users.enums.UserStatus;
import com.keningarcia.restaurant_management_system.users.mapper.UserMapper;
import com.keningarcia.restaurant_management_system.users.repository.UserRepository;
import com.keningarcia.restaurant_management_system.users.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Page<UserResponse> findAll(String username, String email, Boolean active, Pageable pageable) {
        var spec = UserSpecification.withFilters(username, email, active);
        return userRepository.findAll(spec, pageable)
                .map(userMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public UserResponse findById(Long id) {
        return userMapper.toResponse(findUser(id));
    }

    @Transactional
    public UserResponse create(UserRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new DuplicateResourceException("El username ya esta en uso");
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("El email ya esta en uso");
        }

        Set<Role> roles = request.roleIds().stream()
                .map(id -> roleRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado: " + id)))
                .collect(Collectors.toSet());

        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .fullName(request.fullName())
                .status(UserStatus.ACTIVE)
                .roles(roles)
                .active(true)
                .build();

        return userMapper.toResponse(userRepository.save(user));
    }

    @Transactional
    public UserResponse update(Long id, UserRequest request) {
        User user = findUser(id);

        if (!user.getUsername().equals(request.username()) && userRepository.existsByUsername(request.username())) {
            throw new DuplicateResourceException("El username ya esta en uso");
        }
        if (!user.getEmail().equals(request.email()) && userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("El email ya esta en uso");
        }

        Set<Role> roles = request.roleIds().stream()
                .map(roleId -> roleRepository.findById(roleId)
                        .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado: " + roleId)))
                .collect(Collectors.toSet());

        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setFullName(request.fullName());
        if (request.password() != null && !request.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.password()));
        }
        user.setRoles(roles);

        return userMapper.toResponse(userRepository.save(user));
    }

    @Transactional
    public void delete(Long id) {
        User user = findUser(id);
        user.setActive(false);
        userRepository.save(user);
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + id));
    }
}
