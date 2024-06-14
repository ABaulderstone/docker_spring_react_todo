package io.nology.todo_backend.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public Optional<User> loadUserByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Optional<User> loadById(Long id) {
        return repository.findById(id);
    }

}
