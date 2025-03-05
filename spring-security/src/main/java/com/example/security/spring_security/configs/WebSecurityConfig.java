package com.example.security.spring_security.configs;

import com.example.security.spring_security.service.UserService;
import com.example.security.spring_security.seurity.CustomsDetailsServise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private UserService userService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Настройка авторизации
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login").permitAll() // Только /login доступен всем
                        .requestMatchers("/user").hasAnyRole("USER", "ADMIN") // Доступ только для USER
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Доступ только для ADMIN
                        .anyRequest().authenticated() // Все остальные endpoints требуют аутентификации
                )
                // Настройка формы входа (используем стандартную страницу входа Spring Security)
                .formLogin(form -> form
//                        .successHandler(loginSuccessHandler) // Используем LoginSuccessHandler
                       .permitAll() // Разрешить доступ к странице входа всем
                )
                // Настройка выхода
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout") // Перенаправление после выхода
                        .permitAll() // Разрешить выход всем
                )
                // Настройка обработки ошибок
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/access-denied") // Страница для ошибки доступа
                );


        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomsDetailsServise();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

}