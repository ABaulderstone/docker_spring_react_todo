package io.nology.todo_backend.fixtures;

import java.util.HashMap;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import io.nology.todo_backend.auth.CustomUserDetails;
import io.nology.todo_backend.auth.JwtService;
import io.nology.todo_backend.category.Category;
import io.nology.todo_backend.category.CategoryRepository;
import io.nology.todo_backend.factories.CategoryFactory;
import io.nology.todo_backend.factories.TodoFactory;
import io.nology.todo_backend.factories.UserFactory;
import io.nology.todo_backend.todo.TodoRepository;
import io.nology.todo_backend.user.User;
import io.nology.todo_backend.user.UserRepository;

@Component
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
    protected JwtService jwtService;

    @Autowired
    protected UserFactory userFactory;

    @Autowired
    protected CategoryFactory categoryFactory;
    @Autowired
    protected TodoFactory todoFactory;

    protected void addUserToken(String email, String token) {
        this.tokens.put(email, token);
    }

    protected void generateAndStoreToken(User user) {
        CustomUserDetails userDetails = new CustomUserDetails(user);
        String token = this.jwtService.generateToken(userDetails);
        addUserToken(user.getEmail(), token);
    }

    protected User createUserWithToken() {
        User user = this.userFactory.create();
        generateAndStoreToken(user);
        this.userFactory.save(user);
        return user;
    }

    protected void createUniqueCategoriesForUser(int n, User user) {
        HashSet<String> usedHexCodes = new HashSet<String>();

        for (int i = 0; i < n; i++) {
            Category newCategory = categoryFactory.create();
            if (usedHexCodes.contains(newCategory.getColor())) {
                continue;
            }
            newCategory.setUser(user);
            categoryFactory.save(newCategory);
        }

    }

    protected void createTodosForUser(int n, User user) {
        for (int i = 0; i < n; i++) {
            todoFactory.createForUser(user);
        }
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
