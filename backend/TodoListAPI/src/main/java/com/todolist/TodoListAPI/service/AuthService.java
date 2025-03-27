package com.todolist.TodoListAPI.service;

import com.todolist.TodoListAPI.model.User;
import com.todolist.TodoListAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
        @Autowired
        private UserRepository userRepository;

        public boolean authenticate(String username, String password) {
            Optional<User> user = userRepository.findByUsername(username);
            return user.isPresent() && user.get().getPassword().equals(password);
        }

        public User createUser(String username, String password) {
            if (userRepository.findByUsername(username).isPresent()) {
                throw new RuntimeException("Usuário já existe.");
            }

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);


            return userRepository.save(user);
        }
}


