package io.nology.todo_backend.todo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping()
    public ResponseEntity<List<Todo>> findAll() {
        List<Todo> todos = this.todoService.findCurrentUserTodos();
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Todo> createTodo(@RequestBody @Valid CreateTodoDTO data) {
        Todo newTodo = this.todoService.createTodo(data);
        return new ResponseEntity<>(newTodo, HttpStatus.CREATED);
    }

}