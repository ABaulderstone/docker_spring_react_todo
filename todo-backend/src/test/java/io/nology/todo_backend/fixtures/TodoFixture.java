package io.nology.todo_backend.fixtures;

import org.springframework.stereotype.Component;

import io.nology.todo_backend.user.User;
import lombok.Getter;

@Component
public class TodoFixture extends BaseFixture {
    @Getter
    private User user;

    @Override
    public void setup() {
        user = createUserWithToken();
        createUniqueCategoriesForUser(5, user);
        createTodosForUser(20, user);
    }

}
