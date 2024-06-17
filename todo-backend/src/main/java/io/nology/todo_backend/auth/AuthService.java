package io.nology.todo_backend.auth;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.nology.todo_backend.user.CreateUserDTO;
import io.nology.todo_backend.user.UserService;

@Service
public class AuthService {
    private UserService userService;
    private ModelMapper mapper;

    @Autowired
    AuthService(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    public TokenResponseDTO register(RegisterDTO data) {
        // mapper encodes password;
        CreateUserDTO userDTO = mapper.map(data, CreateUserDTO.class);
        System.out.println(userDTO);
        TokenResponseDTO response = new TokenResponseDTO();
        response.setToken("Success");
        return response;
    }

}
