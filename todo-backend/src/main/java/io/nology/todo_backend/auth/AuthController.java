package io.nology.todo_backend.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;

    @Autowired
    AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public TokenResponseDTO register(@Valid @RequestBody RegisterDTO data) {
        return authService.register(data);
    }

    @PostMapping("/login")
    public TokenResponseDTO login(@Valid @RequestBody LoginDTO data) {
        return this.authService.login(data);
    }

}
