package com.keningarcia.restaurant_management_system.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class UserPrincipal extends User {

    private final Long userId;

    public UserPrincipal(Long userId, String username, String password, boolean enabled,
                         Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, true, true, true, authorities);
        this.userId = userId;
    }
}
