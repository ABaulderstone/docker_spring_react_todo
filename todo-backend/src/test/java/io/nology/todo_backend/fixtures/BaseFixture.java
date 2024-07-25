package io.nology.todo_backend.fixtures;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import io.nology.todo_backend.category.CategoryRepository;
import io.nology.todo_backend.factories.UserFactory;
import io.nology.todo_backend.todo.TodoRepository;
import io.nology.todo_backend.user.User;
import io.nology.todo_backend.user.UserRepository;

public abstract class BaseFixture {
    private HashMap<String, String> tokens;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected CategoryRepository categoryRepository;

    @Autowired
    protected TodoRepository todoRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected UserFactory userFactory;

    protected void addUserToken(String email, String token) {
        this.tokens.put(email, token);
    }

    public String getUserToken(String email) {
        return this.tokens.getOrDefault(email, "");
    }

    public BaseFixture() {
        this.tokens = new HashMap<String, String>();
    }

    @Transactional
    public abstract void setup();

    @Transactional
    public void tearDown() {
        todoRepository.deleteAll();
        categoryRepository.deleteAll();
        userRepository.deleteAll();
    }

}
