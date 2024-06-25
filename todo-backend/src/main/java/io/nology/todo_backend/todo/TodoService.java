package io.nology.todo_backend.todo;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.nology.todo_backend.category.Category;
import io.nology.todo_backend.category.CategoryService;
import io.nology.todo_backend.common.BaseService;
import io.nology.todo_backend.user.User;
import io.nology.todo_backend.user.UserService;
import jakarta.validation.Valid;

@Service
public class TodoService extends BaseService {
    private final TodoRepository todoRepository;
    private final UserService userService;
    private final ModelMapper mapper;

    @Autowired
    public TodoService(TodoRepository todoRepository, UserService userService,
            ModelMapper mapper) {
        this.todoRepository = todoRepository;
        this.userService = userService;
        this.mapper = mapper;
    }

    Page<Todo> findCurrentUserTodos(int page, int size) {
        Long currentId = getCurrentUserId();
        Pageable pageable = PageRequest.of(page, size);
        return this.todoRepository.findbyUserId(currentId, pageable);
    }

    public Todo createTodo(@Valid CreateTodoDTO data) throws Exception {
        Todo newTodo = mapper.map(data, Todo.class);
        newTodo.setId(null);
        Long currentId = getCurrentUserId();
        User owner = userService.loadById(currentId).orElseThrow(() -> new Exception("Owner not found"));
        Category jokeCategory = null;
        if (data.getCategoryId() != null) {
            jokeCategory = owner.getCategories().stream().filter((c) -> c.getId() == data.getCategoryId())
                    .findFirst().orElseThrow(() -> new Exception("Category does not belong to owner"));
        }
        newTodo.setUser(owner);
        newTodo.setCategory(jokeCategory);
        return todoRepository.save(newTodo);
    }

}
