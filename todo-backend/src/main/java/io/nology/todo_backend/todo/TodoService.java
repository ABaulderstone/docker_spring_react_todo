package io.nology.todo_backend.todo;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import io.nology.todo_backend.user.User;
import jakarta.validation.Valid;

@Service
public class TodoService {
    private final TodoRepository todoRepository;
    private final ModelMapper mapper;

    @Autowired
    public TodoService(TodoRepository todoRepository, ModelMapper mapper) {
        this.todoRepository = todoRepository;
        this.mapper = mapper;
    }

    List<Todo> findCurrentUserTodos() {
        Authentication authenticationObj = SecurityContextHolder.getContext().getAuthentication();
        String currentId = (String) authenticationObj.getPrincipal();

        return this.todoRepository.findbyUserId(Long.parseLong(currentId));
    }

    public Todo createTodo(@Valid CreateTodoDTO data) {
        Todo newTodo = mapper.map(data, Todo.class);
        User owner = new User();
        Authentication authenticationObj = SecurityContextHolder.getContext().getAuthentication();
        String currentId = (String) authenticationObj.getPrincipal();
        owner.setId(Long.parseLong(currentId));
        newTodo.setUser(owner);
        return todoRepository.save(newTodo);
    }

}
