package io.nology.todo_backend.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.util.ReflectionTestUtils;

import io.nology.todo_backend.user.CreateUserDTO;
import io.nology.todo_backend.user.User;
import io.nology.todo_backend.user.UserService;

public class AuthServiceUnitTest {
    @Mock
    private UserService userService;
    @Mock
    private JwtService jwtService;
    @Mock
    private ModelMapper mapper;
    @Mock
    private AuthenticationManager authManager;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(authService, "userDetailsService", customUserDetailsService);
    }

    @Test
    void register_success() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setEmail("new_user@test.com");
        registerDTO.setPassword("password");
        CreateUserDTO createUserDTO = new CreateUserDTO();
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("new_uesr@test.com");

        when(mapper.map(registerDTO, CreateUserDTO.class)).thenReturn(createUserDTO);
        when(userService.create(createUserDTO)).thenReturn(mockUser);
        when(jwtService.generateToken(any(CustomUserDetails.class))).thenReturn("mockedtoken");

        TokenResponseDTO result = authService.register(registerDTO);
        assertNotNull(result);
        assertEquals("mockedtoken", result.getToken());
        verify(userService).create(createUserDTO);
        verify(jwtService).generateToken(any(CustomUserDetails.class));

    }

    @Test
    void register_userCreationFails() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setEmail("new_user@test.com");
        registerDTO.setPassword("password");
        CreateUserDTO createUserDTO = new CreateUserDTO();

        when(mapper.map(registerDTO, CreateUserDTO.class)).thenReturn(createUserDTO);
        when(userService.create(createUserDTO)).thenThrow(new RuntimeException("User Creation Failed"));

        assertThrows(RuntimeException.class, () -> authService.register(registerDTO));
        verify(userService).create(createUserDTO);
        verify(jwtService, never()).generateToken(any());
    }

    @Test
    void register_tokenGenerationFails() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setEmail("new_user@test.com");
        registerDTO.setPassword("password");
        CreateUserDTO createUserDTO = new CreateUserDTO();
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("new_uesr@test.com");

        when(mapper.map(registerDTO, CreateUserDTO.class)).thenReturn(createUserDTO);
        when(userService.create(createUserDTO)).thenReturn(mockUser);
        when(jwtService.generateToken(any(CustomUserDetails.class))).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> authService.register(registerDTO));
        verify(userService).create(createUserDTO);
        verify(jwtService).generateToken(any(CustomUserDetails.class));
    }

    @Test
    void login_success() {
        String mockEmail = "valid_user@email.com";
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail(mockEmail);
        loginDTO.setPassword("validPassword");

        User mockedUser = new User();
        mockedUser.setId(1L);
        mockedUser.setEmail(mockEmail);

        CustomUserDetails userDetails = new CustomUserDetails(mockedUser);

        // when(userService.loadUserByEmail(mockEmail)).thenReturn(Optional.of(mockedUser));
        when(customUserDetailsService.loadUserByUsername(mockEmail)).thenReturn(userDetails);
        when(authManager.authenticate(any(Authentication.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(userDetails, null));
        when(jwtService.generateToken(userDetails)).thenReturn("mockedToken");
        TokenResponseDTO result = authService.login(loginDTO);
        assertNotNull(result);
        assertEquals("mockedToken", result.getToken());
        verify(authManager).authenticate(any(Authentication.class));
        verify(jwtService).generateToken(userDetails);
    }

    @Test
    void login_invalidEmail() {
        String invalidEmail = "nonexistent@email.com";
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail(invalidEmail);
        loginDTO.setPassword("somePassword");

        when(customUserDetailsService.loadUserByUsername(invalidEmail))
                .thenThrow(new UsernameNotFoundException("User not found with email: " + invalidEmail));

        assertThrows(UsernameNotFoundException.class, () -> authService.login(loginDTO));
        verify(customUserDetailsService).loadUserByUsername(invalidEmail);
        verify(authManager, never()).authenticate(any());
        verify(jwtService, never()).generateToken(any());

    }

    @Test
    void login_authentication_fails() {
        String email = "real_user@test.com";
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail(email);
        loginDTO.setPassword("somePassword");

        User mockedUser = new User();
        mockedUser.setEmail(email);
        CustomUserDetails userDetails = new CustomUserDetails(mockedUser);

        when(customUserDetailsService.loadUserByUsername(email)).thenReturn(userDetails);
        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid password"));

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> authService.login(loginDTO));

        verify(customUserDetailsService).loadUserByUsername(email);
        verify(authManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, never()).generateToken(any());
    }
}
