package com.example.security.spring_security.controller;

import com.example.security.spring_security.model.Role;
import com.example.security.spring_security.model.User;
import com.example.security.spring_security.repositories.RoleRepository;
import com.example.security.spring_security.service.UserService;
import com.example.security.spring_security.service.UserServiceImpl;
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

    private final UserServiceImpl userServiceImpl;
    private RoleRepository roleRepository;
    @Autowired
    public UserController(UserService userService, RoleRepository roleRepository, UserServiceImpl userServiceImpl) {
        this.userService = userService;
        this.roleRepository=roleRepository;
        this.userServiceImpl = userServiceImpl;
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

    @PostMapping("/addUser")
    @PreAuthorize("hasRole('ADMIN')")
    public String addUser(
            @ModelAttribute("user") User user,
            @RequestParam(value = "roles", required = false) Set<Long> roleIds // Принимаем ID ролей
    ) {
        if (roleIds != null && !roleIds.isEmpty()) {
           userService.setRolesForUser(user,roleIds);
        } else {
            user.setRoles(Set.of()); // Если роли не переданы, устанавливаем пустой набор
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
        List<Role> allRoles = roleRepository.findAll();
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("allRoles", allRoles);
        return "edit_user";
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam("id") int id) {
        userService.update(id, user);
        return "redirect:/affterUpdate?id=" + id;

    }
    @GetMapping("/affterUpdate")
    public String affterUpdate(Model model, @RequestParam("id") int id) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "selected_user"; // Return the view name
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(@ModelAttribute("user") User user, @RequestParam("id") int id) {
        userService.delete(id);
        return "redirect:/affterDelite";
    }
    @GetMapping("/affterDelite")
    @PreAuthorize("hasRole('ADMIN')")
    public String affterDelite(Model model) {
        model.addAttribute("users", userService.findAll());
        return "all_users";
    }
    @GetMapping("/user")
    public String ordinarUser(Model model) {
        UserDetails userDetails = userService.getUserDetails();
        model.addAttribute("user", userService.findByEmail(userDetails.getUsername())); // Используем findByEmail
        return "ordinar_user";
    }


    @GetMapping("/login")
    public String login() {
        return "login"; // Имя шаблона (login.html)
    }
}


