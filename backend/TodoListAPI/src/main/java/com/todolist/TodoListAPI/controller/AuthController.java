package com.todolist.TodoListAPI.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register"; // Correspondente ao arquivo src/main/resources/templates/register.html
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Correspondente ao arquivo src/main/resources/templates/login.html
    }
}
