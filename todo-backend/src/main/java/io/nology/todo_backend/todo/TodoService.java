package io.nology.todo_backend.todo;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import io.nology.todo_backend.common.BaseService;
import io.nology.todo_backend.user.User;
import jakarta.validation.Valid;

@Service
public class TodoService extends BaseService {
    private final TodoRepository todoRepository;
    private final ModelMapper mapper;

    @Autowired
    public TodoService(TodoRepository todoRepository, ModelMapper mapper) {
        this.todoRepository = todoRepository;
        this.mapper = mapper;
    }

    List<Todo> findCurrentUserTodos() {
        Long currentId = getCurrentUserId();
        return this.todoRepository.findbyUserId(currentId);
    }

    public Todo createTodo(@Valid CreateTodoDTO data) {
        Todo newTodo = mapper.map(data, Todo.class);
        User owner = new User();
        Long currentId = getCurrentUserId();
        owner.setId(currentId);
        newTodo.setUser(owner);
        return todoRepository.save(newTodo);
    }

}
