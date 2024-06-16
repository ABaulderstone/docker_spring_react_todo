package io.nology.todo_backend.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.nology.todo_backend.user.UserService;

@Service
public class AuthService {
    private UserService userService;

    @Autowired
    AuthService(UserService userService) {
        this.userService = userService;
    }

    public TokenResponseDTO register(RegisterDTO data) {
        TokenResponseDTO response = new TokenResponseDTO();
        response.setToken("Success");
        return response;
    }

}
