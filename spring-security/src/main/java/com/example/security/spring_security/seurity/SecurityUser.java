package com.example.security.spring_security.seurity;

import com.example.security.spring_security.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class SecurityUser implements UserDetails {

    private final User user;

    public SecurityUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Реализуйте преобразование ролей пользователя в GrantedAuthority
        return user.getRoles();
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // Предполагая, что User имеет метод getPassword()
    }

    @Override
    public String getUsername() {
        return user.getUserName(); // Предполагая, что User имеет метод getUsername()
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Или логика из вашего User
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Или логика из вашего User
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Или логика из вашего User
    }

    @Override
    public boolean isEnabled() {
        return true;// Предполагая, что User имеет метод isEnabled()
    }

    public User getUser() {
        return user;
    }
}