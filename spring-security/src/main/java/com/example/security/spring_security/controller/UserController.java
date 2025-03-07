package com.example.security.spring_security.controller;

import com.example.security.spring_security.model.Role;
import com.example.security.spring_security.model.User;
import com.example.security.spring_security.repositories.RoleRepository;
import com.example.security.spring_security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@Controller

public class UserController {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    private UserService userService;


    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "all_users";
    }

    @GetMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String showFormAddUser(Model model) {
        // Получаем все роли из базы данных
        List<Role> allRoles = roleRepository.findAll();

        // Передаем список ролей в модель
        model.addAttribute("allRoles", allRoles);

        // Передаем пустой объект User для формы
        model.addAttribute("user", new User());

        return "add_user"; // Имя Thymeleaf шаблона
    }

    @PostMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    public String addUser(@ModelAttribute("user") User user, @RequestParam(value = "roles", required = false) Set<String> roleNames) {
        // Если роли переданы, преобразуем их в объекты Role
        if (roleNames != null && !roleNames.isEmpty()) {
            Set<Role> roles = roleNames.stream()
                    .map(roleName -> roleRepository.findByRoleName(roleName)) // Используем roleRepository
                    .filter(Optional::isPresent) // Отфильтровываем пустые Optional
                    .map(Optional::get) // Извлекаем Role из Optional
                    .collect(Collectors.toSet());
            user.setRoles(roles); // Устанавливаем роли для пользователя
        } else {
            // Если роли не переданы, устанавливаем пустой набор ролей
            user.setRoles(Set.of());
        }

        userService.save(user); // Сохраняем пользователя
        return "redirect:/admin"; // Перенаправляем на страницу админа
    }


    @GetMapping("/show")
    @PreAuthorize("hasRole('ADMIN')")
    public String showUserById(@RequestParam("id") int id, Model model) {
        User user = userService.findById(id);
        if (user == null) {
            return "redirect:/user";
        } else {
            model.addAttribute("user", user);
            return "selected_user";
        }
    }

    @GetMapping("edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String editUser(Model model, @RequestParam("id") int id) {
        model.addAttribute("user", userService.findById(id));
        return "edit_user";
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam("id") int id) {
        userService.update(id, user);
        return "redirect:/user";
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(@ModelAttribute("user") User user, @RequestParam("id") int id) {
        userService.delete(id);
        return "redirect:/user";
    }
    @GetMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    public String affterDelite(Model model) {
        model.addAttribute("users", userService.findAll());
        return "all_users";
    }
    @GetMapping("/ordinaruser")
    public String ordinarUser(Model model) {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", userService.findByUserName(userDetails.getUsername()));
        return "ordinar_user";
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Имя шаблона (login.html)
    }
}


