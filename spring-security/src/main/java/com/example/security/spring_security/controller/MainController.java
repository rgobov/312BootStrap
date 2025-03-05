package com.example.security.spring_security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/")
    public String homePage() {
        return "redirect:/login";
    }

    @GetMapping("/aut")
    public String autPage() {
        return "secured prt of web service";
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Возвращает шаблон login.html
    }

}
