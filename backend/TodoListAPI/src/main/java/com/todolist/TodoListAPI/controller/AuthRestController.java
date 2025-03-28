package com.todolist.TodoListAPI.controller;

import com.todolist.TodoListAPI.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    private final AuthService authService;

    public AuthRestController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        boolean isAuthenticated = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());

        if (isAuthenticated) {
            return ResponseEntity.ok("Login bem-sucedido!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestParam("username") String username,
                                               @RequestParam("password") String password) {
        try {
            // Chama o serviço para criar o usuário
            authService.createUser(username, password);
            return ResponseEntity.ok("Usuário registrado com sucesso! Faça login.");
        } catch (Exception e) {
            // Caso ocorra algum erro, retorna um erro apropriado
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao registrar usuário: " + e.getMessage());
        }
    }
}
