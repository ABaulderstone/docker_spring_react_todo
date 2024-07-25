package io.nology.todo_backend.fixtures;

import java.util.HashMap;

import io.nology.todo_backend.user.User;
import lombok.Getter;

public class AuthFixture extends BaseFixture {
    @Getter
    private HashMap<String, String> rawPasswords;
    @Getter
    private User user;

    @Override
    public void setup() {
        User testUser = userFactory.createAndSave();
        this.rawPasswords = userFactory.getRawPasswords();
        this.user = testUser;
    }

}
