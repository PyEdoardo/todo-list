package com.todolist.TodoListAPI.controller;

import com.todolist.TodoListAPI.model.Todo;
import com.todolist.TodoListAPI.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/{userId}")
    public List<Todo> getTodos(@PathVariable Long userId) {
        return todoService.getTodosByUserId(userId);
    }

    @GetMapping("/")
    public String showTodoList(Model model){
        model.addAttribute("todos", todoService.getTodos());
        return "todo";
    }

    @PostMapping("/todos")
    public String addTodo(@RequestParam Todo todo) {
        todoService.addTodo(todo);
        return "redirect:/";
    }

    @PutMapping("/")
    public Todo updateTodo(@RequestBody Todo todo) {
        return todoService.updateTodo(todo);
    }

    @GetMapping("/todos/{id}/delete")
    public String deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return "redirect:/";
    }
}
