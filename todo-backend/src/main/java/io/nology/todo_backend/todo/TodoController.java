package io.nology.todo_backend.todo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.nology.todo_backend.common.PaginatedResponse;
import io.nology.todo_backend.common.PaginationUtils;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;
    private final PaginationUtils paginationUtils;

    @Autowired
    public TodoController(TodoService todoService, PaginationUtils paginationUtils) {
        this.todoService = todoService;
        this.paginationUtils = paginationUtils;
    }

    @GetMapping()
    public ResponseEntity<PaginatedResponse<Todo>> findAll(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {

        Page<Todo> todos = this.todoService.findCurrentUserTodos(page - 1, size);
        PaginatedResponse<Todo> response = paginationUtils.generatePaginatedResponse(todos, page);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Todo> createTodo(@RequestBody @Valid CreateTodoDTO data) throws Exception {
        Todo newTodo = this.todoService.createTodo(data);
        return new ResponseEntity<>(newTodo, HttpStatus.CREATED);
    }

}
