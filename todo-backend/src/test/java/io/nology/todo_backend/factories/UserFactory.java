package io.nology.todo_backend.factories;

import java.util.HashMap;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import io.nology.todo_backend.user.User;
import io.nology.todo_backend.user.UserRepository;
import lombok.Getter;

@Component
public class UserFactory extends BaseFactory<User> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Getter
    private final HashMap<String, String> rawPasswords;

    public UserFactory(Faker faker, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        super(faker);
        this.rawPasswords = new HashMap<String, String>();
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User create() {
        String email = faker.internet().emailAddress();
        String rawPassword = faker.internet().password(8, 20, true, true, true);
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(rawPassword));
        rawPasswords.put(email, rawPassword);
        return newUser;

    }

    public User createAndSave() {
        return this.userRepository.save(create());
    }

    public void save(User user) {
        this.userRepository.save(user);
    }

}
