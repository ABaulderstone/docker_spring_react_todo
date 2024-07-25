package io.nology.todo_backend.factories;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.github.javafaker.Faker;

import io.nology.todo_backend.category.Category;
import io.nology.todo_backend.todo.Todo;
import io.nology.todo_backend.todo.TodoRepository;
import io.nology.todo_backend.user.User;

public class TodoFactory extends BaseFactory<Todo> {
    private TodoRepository todoRepository;

    public TodoFactory(Faker faker, TodoRepository todoRepository) {
        super(faker);
        this.todoRepository = todoRepository;
    }

    @Override
    public Todo create() {
        Todo newTodo = new Todo();
        newTodo.setDueDate(faker.date().future(20, TimeUnit.DAYS));
        newTodo.setTitle(faker.lorem().sentence(6));
        return newTodo;
    }

    public Todo createForUser(User user) {
        Todo newTodo = create();
        Random rand = new Random();
        List<Category> categories = user.getCategories();
        newTodo.setUser(user);
        newTodo.setCategory(categories.get(rand.nextInt(categories.size())));
        return this.todoRepository.save(newTodo);
    }

}
