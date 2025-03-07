package com.example.security.spring_security.configs;

import com.example.security.spring_security.model.Role;
import com.example.security.spring_security.model.User;
import com.example.security.spring_security.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CreateAdmin {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder; // Добавляем энкодер

    public CreateAdmin(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        Role adminRole = new Role("ROLE_ADMIN");
        Set<Role> admninroles = new HashSet<>();
        admninroles.add(adminRole);
        Role userRole = new Role("ROLE_USER");
        Set<Role> userroles = new HashSet<>();
        userroles.add(userRole);


        // Кодируем пароль перед сохранением
        User admin = new User(
                "Ivan",
                "ivan@example.com",
                passwordEncoder.encode("Ivan"), // Кодирование пароля
                admninroles,32,"Ivan"
        );
        User user = new User(
                "Yn",
                "Yan@example.com",
                passwordEncoder.encode("Yn"), // Кодирование пароля
                userroles, 50,"Yn"
        );

        userRepository.save(admin);
        userRepository.save(user);
    }
}