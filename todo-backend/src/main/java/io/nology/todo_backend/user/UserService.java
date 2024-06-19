package io.nology.todo_backend.user;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository repository;
    private ModelMapper mapper;

    @Autowired
    UserService(UserRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Optional<User> loadUserByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Optional<User> loadById(Long id) {
        return repository.findById(id);
    }

    public User create(CreateUserDTO data) {
        User newUser = mapper.map(data, User.class);
        return this.repository.save(newUser);
    }

}
