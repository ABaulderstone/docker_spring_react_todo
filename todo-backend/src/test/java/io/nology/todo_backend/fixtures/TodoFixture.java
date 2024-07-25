package io.nology.todo_backend.fixtures;

import io.nology.todo_backend.user.User;
import lombok.Getter;

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
