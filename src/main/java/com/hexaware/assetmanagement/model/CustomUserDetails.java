package com.hexaware.assetmanagement.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Map roles to authorities
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Implement custom logic if needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Implement custom logic if needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Implement custom logic if needed
    }

    @Override
    public boolean isEnabled() {
        return true; // Implement custom logic if needed
    }

    public User getUser() {
        return user; // Return the actual user entity if needed
    }
}
