package com.example.security.spring_security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class PersonDetails implements UserDetails {

    private final User user;

    public PersonDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getUser().getRoles();
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // Предполагая, что User имеет метод getPassword()
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // Используем email вместо userName
    }

    public long getId(){
        return user.getId();
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

    public int getAge(){
        return user.getAge();
    }
    public String getLastName(){
        return user.getLastName();
    }

    public User getUser() {
        return user;
    }
}