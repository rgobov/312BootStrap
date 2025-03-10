package com.example.security.spring_security.seurity;

import com.example.security.spring_security.model.PersonDetails;
import com.example.security.spring_security.model.User;
import com.example.security.spring_security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomsDetailsServise implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public void setCustomDetailsService(UserRepository userRepository) { // Исправлено имя
        this.userRepository = userRepository;
    }
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("CustomsDetailsServise: Looking for user with email: " + email); // Логирование
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            System.out.println("CustomsDetailsServise: User not found with email: " + email);
            throw new UsernameNotFoundException("Пользователь с email " + email + " не найден");
        }
        System.out.println("CustomsDetailsServise: User found: " + user.get());
        return new PersonDetails(user.get());
    }
}


