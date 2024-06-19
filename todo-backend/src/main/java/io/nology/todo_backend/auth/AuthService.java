package io.nology.todo_backend.auth;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import io.nology.todo_backend.user.CreateUserDTO;
import io.nology.todo_backend.user.User;
import io.nology.todo_backend.user.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;

@Service
public class AuthService {
    private UserService userService;
    private JwtService jwtService;
    private ModelMapper mapper;
    private AuthenticationManager authManager;
    private CustomUserDetailsService userDetailsService;

    @Autowired
    AuthService(UserService userService, JwtService jwtService, ModelMapper mapper, AuthenticationManager authManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.mapper = mapper;
        this.authManager = authManager;
    }

    @PostConstruct
    private void init() {
        this.userDetailsService = new CustomUserDetailsService(userService);
    }

    public TokenResponseDTO register(RegisterDTO data) {
        // mapper encodes password;
        CreateUserDTO userDTO = mapper.map(data, CreateUserDTO.class);
        User createdUser = this.userService.create(userDTO);
        CustomUserDetails userDetails = new CustomUserDetails(createdUser);
        String token = this.jwtService.generateToken(userDetails);
        TokenResponseDTO response = new TokenResponseDTO();
        response.setToken(token);
        return response;
    }

    public TokenResponseDTO login(@Valid LoginDTO data) {
        CustomUserDetails userDetails = (CustomUserDetails) this.userDetailsService.loadUserByUsername(data.getEmail());
        System.out.println(userDetails);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(data.getEmail(),
                data.getPassword());
        System.out.println(authToken);
        this.authManager.authenticate(authToken);
        TokenResponseDTO response = new TokenResponseDTO();
        String token = this.jwtService.generateToken(userDetails);
        response.setToken(token);
        return response;

    }

}
