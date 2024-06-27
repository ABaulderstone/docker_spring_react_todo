package io.nology.todo_backend.config;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import io.nology.todo_backend.category.Category;
import io.nology.todo_backend.category.CategoryRepository;
import io.nology.todo_backend.todo.Todo;
import io.nology.todo_backend.todo.TodoRepository;
import io.nology.todo_backend.user.User;
import io.nology.todo_backend.user.UserRepository;

@Component
public class TestDataLoader {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TodoRepository todoRepository;
    private final PasswordEncoder passwordEncoder;

    private Map<String, User> users = new HashMap<>();
    private Map<String, String> rawPasswords = new HashMap<>();
    private Map<String, Category> categories = new HashMap<>();
    private Map<String, Todo> todos = new HashMap<>();

    @Autowired
    public TestDataLoader(UserRepository userRepository, CategoryRepository categoryRepository,
            TodoRepository todoRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.todoRepository = todoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getPlainUser() {
        return users.get("plainUser");
    }

    public User getUser(String key) {
        return users.get(key);
    }

    public void loadData() {
        createTodos();
    }

    public void clearData() {
        this.userRepository.deleteAll();
        this.categoryRepository.deleteAll();
        this.todoRepository.deleteAll();
        users.clear();
        rawPasswords.clear();
        categories.clear();
        todos.clear();
    }

    private void createUsers() {
        User plainUser = new User();
        plainUser.setEmail("user1@test.com");
        String rawPass1 = "Password1";
        rawPasswords.put("plainUser", rawPass1);
        plainUser.setPassword(passwordEncoder.encode(rawPass1));

        users.put("plainUser", this.userRepository.save(plainUser));

        User maxCategoryUser = new User();
        maxCategoryUser.setEmail("user2@test.com");
        String rawPass2 = "Password1";
        rawPasswords.put("maxCategoryUser", rawPass2);
        maxCategoryUser.setPassword(passwordEncoder.encode(rawPass2));

        users.put("maxCategoryUser", this.userRepository.save(maxCategoryUser));
    }

    private void createCategories() {
        createUsers();
        Category plainCategory = new Category();
        plainCategory.setColor("#cccccc");
        plainCategory.setName("plain");
        plainCategory.setUser(users.get("plainUser"));
        categories.put("plainCategory", this.categoryRepository.save(plainCategory));

        for (int i = 1; i < 9; i++) {
            Category aCategory = new Category();
            aCategory.setColor("#aaaaa" + i);
            String categoryName = "Test" + i;
            aCategory.setName(categoryName);
            aCategory.setUser(users.get("maxCategoryUser"));
            categories.put(categoryName, this.categoryRepository.save(aCategory));
        }
    }

    private void createTodos() {
        createCategories();
        Todo plainTodo = new Todo();
        plainTodo.setCategory(categories.get("plainCategory"));
        plainTodo.setUser(users.get("plainUser"));
        plainTodo.setTitle("Plain");
        todos.put("plainTodo", this.todoRepository.save(plainTodo));

        User maxCatUser = users.get("maxCategoryUser");

        for (int i = 1; i < 9; i++) {
            Todo testTodo = new Todo();
            testTodo.setTitle("Test Todo " + i);
            Category cat = categories.get("Test" + i);
            testTodo.setCategory(cat);
            testTodo.setUser(maxCatUser);
            todos.put(cat.getName(), this.todoRepository.save(plainTodo));
        }

    }

}
