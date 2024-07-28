package io.nology.todo_backend.fixtures;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import io.nology.todo_backend.user.User;
import lombok.Getter;

@Component
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
