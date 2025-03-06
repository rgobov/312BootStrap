package com.example.security.spring_security.controller;

import com.example.security.spring_security.model.User;
import com.example.security.spring_security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller

public class UserController {

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    private UserService userService;


    @GetMapping("/admin")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "all_users";
    }

    @GetMapping("/new")
    public String showFormAddUser(Model model) {
        model.addAttribute("user", new User());
        return "add_user";
    }

    @PostMapping(("/user"))
    public String addUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/show")
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
    public String editUser(Model model, @RequestParam("id") int id) {
        model.addAttribute("user", userService.findById(id));
        return "edit_user";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam("id") int id) {
        userService.update(id, user);
        return "redirect:/user";
    }

    @PostMapping("/show/delete")
    public String deleteUser(@ModelAttribute("user") User user, @RequestParam("id") int id) {
        userService.delete(id);
        return "redirect:/user";
    }
//    @GetMapping("user/show")
//    public String showUser(@RequestParam("id") Long id, Model model) {
//        User user = userService.findById(id); // Метод для получения пользователя по ID
//        model.addAttribute("user", user);
//        return "show"; // Путь к шаблону (Thymeleaf)
//    }
}
