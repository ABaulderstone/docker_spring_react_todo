package io.nology.todo_backend.todo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    List<Todo> findCurrentUserTodos() {
        Authentication authenticationObj = SecurityContextHolder.getContext().getAuthentication();
        String currentId = (String) authenticationObj.getPrincipal();

        return this.todoRepository.findbyUserId(Long.parseLong(currentId));
    }
}
