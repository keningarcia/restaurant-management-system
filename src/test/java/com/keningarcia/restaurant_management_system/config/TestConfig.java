package com.keningarcia.restaurant_management_system.config;

import com.keningarcia.restaurant_management_system.roles.entity.Role;
import com.keningarcia.restaurant_management_system.roles.enums.RoleEnum;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

@TestConfiguration
public class TestConfig {

    @Bean
    public UserDetailsService testUserDetailsService() {
        return username -> new User(username, "password", List.of(
                new SimpleGrantedAuthority("ADMIN")));
    }
}
