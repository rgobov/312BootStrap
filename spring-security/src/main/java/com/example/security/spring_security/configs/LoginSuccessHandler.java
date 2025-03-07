package com.example.security.spring_security.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;  // Исправлено на jakarta
import jakarta.servlet.http.HttpServletResponse; // Исправлено на jakarta
import jakarta.servlet.ServletException;         // Исправлено на jakarta

import java.io.IOException;
import java.util.Collection;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // Получаем роли пользователя
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Проверяем, есть ли у пользователя роль ADMIN
        boolean isAdmin = authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        // Проверяем, есть ли у пользователя роль USER
        boolean isUser = authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"));

        // Перенаправляем в зависимости от роли
        if (isAdmin) {
            response.sendRedirect("/admin");
        } else if (isUser) {
            response.sendRedirect("/user");
        } else {
            // Если роль не определена, перенаправляем на страницу по умолчанию
            response.sendRedirect("/user");
        }
    }
}