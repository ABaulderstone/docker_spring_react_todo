package io.nology.todo_backend.auth;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.nology.todo_backend.user.CreateUserDTO;
import io.nology.todo_backend.user.User;
import io.nology.todo_backend.user.UserService;

@Service
public class AuthService {
    private UserService userService;
    private JwtService jwtService;
    private ModelMapper mapper;

    @Autowired
    AuthService(UserService userService, JwtService jwtService, ModelMapper mapper) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.mapper = mapper;
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

}
